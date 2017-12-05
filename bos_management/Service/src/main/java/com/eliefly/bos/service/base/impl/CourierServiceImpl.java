package com.eliefly.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.eliefly.bos.dao.base.CourierRepository;
import com.eliefly.bos.domain.base.Courier;
import com.eliefly.bos.service.base.CourierService;

/**
 * ClassName:CourierServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月1日 上午10:22:22 <br/>
 */

@Service
@Transactional
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;

    /*
     * 保存Courier
     */
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    /*
     * 分页查询快递员
     */
    @Override
    public Page<Courier> findAll(Specification<Courier> spec,
            Pageable pageable) {
        return courierRepository.findAll(spec, pageable);
    }

    /*
     * 删除快递员
     */
    @Override
    public void delete(String ids) {
        if (!StringUtils.isEmpty(ids)) {
            String[] idsArr = ids.split(",");
            for (String id : idsArr) {
                courierRepository.updateDelTagJPQL('1', Long.parseLong(id));
            }
        }
    }

    @Override
    public List<Courier> findValidCourier() {

        return courierRepository.findValidCourier();
    }

}
