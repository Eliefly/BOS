package com.eliefly.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.base.CourierRepository;
import com.eliefly.bos.dao.base.FixAreaRepository;
import com.eliefly.bos.dao.base.SubAreaRepository;
import com.eliefly.bos.domain.base.FixedArea;
import com.eliefly.bos.domain.base.SubArea;
import com.eliefly.bos.service.base.FixAreaService;

/**
 * ClassName:fixAreaServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月2日 下午8:16:02 <br/>
 */

@Service
@Transactional
public class FixAreaServiceImpl implements FixAreaService {

    @Autowired
    private FixAreaRepository fixAreaRepository;

    @Autowired
    private SubAreaRepository subAreaRepository;

    @Autowired
    private CourierRepository courierRepository;

    /*
     * 定区保存
     */
    @Override
    public void save(FixedArea fixedArea) {
        fixAreaRepository.save(fixedArea);
    }

    /*
     * 定区分页查询
     */
    @Override
    public Page<FixedArea> pageQuery(Pageable pageable) {
        return fixAreaRepository.findAll(pageable);
    }

    /*
     * 指定快递员关联到定区, 同时关联取派时间到快递员
     */
    @Override
    public void associationCourierToFixedArea(Long id, Long courierId,
            Long takeTimeId) {

        // 此处采用了原生的sql语句操作, 也可利用JPA实体类的持久态来操作
        fixAreaRepository.associationCourierToFixedArea(id, courierId);

        courierRepository.associationTakeTimeToCourier(courierId, takeTimeId);
    }

    /*
     * 查询未关联定区的分区
     */
    @Override
    public List<SubArea> findNoassociationSubArea() {

        return subAreaRepository.findNoassociationSubArea();
    }

    /*
     * 查询已关联定区的分区
     */
    @Override
    public List<SubArea> findAssociationSubArea(Long id) {

        return subAreaRepository.findAssociationSubArea(id);
    }

    @Override
    public void assignSubAreas2FixedArea(Long fixedAreaId,
            List<Long> subAreaIds) {

        // 1. 先把指定id的区域的所有分区解绑
        subAreaRepository.unAssignAllSubAreasByFixedArea(fixedAreaId);

        // 2. 把右边窗口所有的客户都管关联到指定的区域中
        for (Long subAreaId : subAreaIds) {
            subAreaRepository.assignSubArea2FixedArea(fixedAreaId, subAreaId);
        }

    }

}
