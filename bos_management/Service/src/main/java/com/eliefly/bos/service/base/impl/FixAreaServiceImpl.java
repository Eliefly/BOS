package com.eliefly.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * 定区关联快递员
     */
    @Override
    public void associationCourierToFixedArea(Long id, Long courierId) {

        fixAreaRepository.associationCourierToFixedArea(id, courierId);
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
