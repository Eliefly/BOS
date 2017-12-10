package com.eliefly.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.base.AreaRepository;
import com.eliefly.bos.dao.base.FixAreaRepository;
import com.eliefly.bos.dao.take_delivery.OrderRepository;
import com.eliefly.bos.dao.take_delivery.WorkBillRepository;
import com.eliefly.bos.domain.base.Area;
import com.eliefly.bos.domain.base.Courier;
import com.eliefly.bos.domain.base.FixedArea;
import com.eliefly.bos.domain.base.SubArea;
import com.eliefly.bos.domain.take_delivery.Order;
import com.eliefly.bos.domain.take_delivery.WorkBill;
import com.eliefly.bos.service.take_delivery.OrderService;

/**
 * ClassName:OrderServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月10日 下午4:26:32 <br/>
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private FixAreaRepository fixAreaRepository;

    @Autowired
    private WorkBillRepository workBillRepository;

    @Override
    public void saveOrder(Order order) {

        // 把 Order 中 transient 态的 Area 对象数据转换为 持久态的
        Area sendArea = order.getSendArea();
        if (sendArea != null) {

            Area sendAreaDB = areaRepository.findByProvinceAndCityAndDistrict(
                    sendArea.getProvince(), sendArea.getCity(),
                    sendArea.getDistrict());
            order.setSendArea(sendAreaDB);
        }

        Area recArea = order.getRecArea();
        if (recArea != null) {

            Area recAreaDB = areaRepository.findByProvinceAndCityAndDistrict(
                    recArea.getProvince(), recArea.getCity(),
                    recArea.getDistrict());
            order.setRecArea(recAreaDB);
        }

        order.setOrderNum(
                UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        order.setOrderTime(new Date());
        // 保存订单
        orderRepository.save(order);

        // 自动分单一: 根据详细地址自动分单
        String sendAddress = order.getSendAddress();

        if (StringUtils.isNoneEmpty(sendAddress)) {
            String fixedAreaId = WebClient
                    .create("http://localhost:8180/crm/webservice/customerService/findFixedAreaIdByAddress")
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .query("address", sendAddress).get(String.class);

            if (StringUtils.isNoneEmpty(fixedAreaId)) {

                FixedArea fixedArea =
                        fixAreaRepository.findOne(Long.parseLong(fixedAreaId));

                // 由定区获取关联的快递员
                Set<Courier> couriers = fixedArea.getCouriers();

                if (!couriers.isEmpty()) {

                    // 自动创建订单
                    createWorkBill(order, couriers);

                    return;

                }

            }

        }

        // 自动分单2: 根据分区关键字进行自动分单
        // 用户在选择发件区域的时候, 对应的区域必须在数据库中存在
        // 遍历区域中的所有的分区, 分区的关键字和辅助关键字获取出来, 比对地址
        Area sendAreaDB = order.getSendArea();
        // 获取区域下的分区
        Set<SubArea> subareas = sendAreaDB.getSubareas();

        for (SubArea subArea : subareas) {
            // 关键字
            String keyWords = subArea.getKeyWords();
            // 辅助关键字
            String assistKeyWords = subArea.getAssistKeyWords();

            // 由关键字信息 确定定区

            if (sendAddress.contains(keyWords)
                    || sendAddress.contains(assistKeyWords)) {

                FixedArea fixedArea = subArea.getFixedArea();

                // 由定区获取关联的快递员
                Set<Courier> couriers = fixedArea.getCouriers();

                if (!couriers.isEmpty()) {

                    // 自动创建订单
                    createWorkBill(order, couriers);

                    return;

                }

            }

        }

        order.setOrderType("人工分单");

    }

    protected void createWorkBill(Order order, Set<Courier> couriers) {
        Iterator<Courier> iterator = couriers.iterator();
        Courier courier = iterator.next();

        // 设定订单对应的快递员
        order.setCourier(courier);

        WorkBill workBill = new WorkBill();
        workBill.setAttachbilltimes(0);
        workBill.setBuildtime(new Date());
        workBill.setCourier(courier);
        workBill.setOrder(order);
        workBill.setPickstate("新单");
        workBill.setRemark(order.getRemark());
        workBill.setSmsNumber("123");
        workBill.setType("新");

        // 保存工单
        workBillRepository.save(workBill);
        // 设置分单类型
        order.setOrderType("自动分单");

        // 发送工单信息给快递员,此处打印日志进行模拟
        System.out.println("工单信息:请到" + order.getSendAddress() + "取件,客户电话:"
                + order.getSendMobile());
    }

}
