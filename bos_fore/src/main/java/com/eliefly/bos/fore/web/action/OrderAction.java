package com.eliefly.bos.fore.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.eliefly.bos.domain.base.Area;
import com.eliefly.bos.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ClassName:OrderAction <br/>
 * Function: <br/>
 * Date: 2017年12月10日 下午3:25:01 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

    private static final long serialVersionUID = 1696422900447127831L;

    private Order model;

    private String sendAreaInfo;

    private String recAreaInfo;

    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    @Override
    public Order getModel() {
        if (model == null) {
            model = new Order();
        }

        return model;
    }

    /*
     * 添加订单
     */
    @Action(value = "orderAction_save", results = {@Result(name = "success",
            location = "/index.html", type = "redirect")})
    public String save() {

        if (StringUtils.isNotEmpty(sendAreaInfo)) {
            Area sendArea = new Area();
            String[] split = sendAreaInfo.split("/");

            String province = split[0].substring(0, split[0].length() - 1);
            String city = split[1].substring(0, split[1].length() - 1);
            String district = split[2].substring(0, split[2].length() - 1);

            sendArea.setProvince(province);
            sendArea.setCity(city);
            sendArea.setDistrict(district);
            model.setSendArea(sendArea);
            
        }

        if (StringUtils.isNotEmpty(recAreaInfo)) {
            Area recArea = new Area();
            String[] split = recAreaInfo.split("/");

            String province = split[0].substring(0, split[0].length() - 1);
            String city = split[1].substring(0, split[1].length() - 1);
            String district = split[2].substring(0, split[2].length() - 1);

            recArea.setProvince(province);
            recArea.setCity(city);
            recArea.setDistrict(district);

            model.setRecArea(recArea);
        }

        // 调用 WebService 保存订单
        WebClient
                .create("http://localhost:8080/Web/webservice/orderService/saveOrder")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).post(model);

        return SUCCESS;

    }

}
