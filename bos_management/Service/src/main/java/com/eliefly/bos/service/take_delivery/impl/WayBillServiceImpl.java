package com.eliefly.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.bos.dao.take_delivery.WayBillRepository;
import com.eliefly.bos.domain.take_delivery.WayBill;
import com.eliefly.bos.service.take_delivery.WayBillService;

/**
 * ClassName:WayBillServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月12日 下午5:20:06 <br/>
 */
@Transactional
@Service
public class WayBillServiceImpl implements WayBillService {

    @Autowired
    private WayBillRepository wayBillRepository;

    @Override
    public void save(WayBill model) {
        
        wayBillRepository.save(model);
    }

}
