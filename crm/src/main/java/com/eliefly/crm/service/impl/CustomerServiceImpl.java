package com.eliefly.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliefly.crm.dao.CustomerDao;
import com.eliefly.crm.domain.Customer;
import com.eliefly.crm.service.CustomerService;

/**
 * ClassName:CustomerServiceImpl <br/>
 * Function: <br/>
 * Date: 2017年12月3日 下午5:19:35 <br/>
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> findAll() {

        return customerDao.findAll();
    }

    @Override
    public List<Customer> findNoassociationCustomer() {

        return customerDao.findNoassociationCustomer();
    }

    @Override
    public List<Customer> findAssociationCustomer(String id) {
        System.out.println("id:" + id);
        return customerDao.findAssociationCustomer(id);
    }

    @Override
    public void assignCustomers2FixedArea(String fixedAreaId,
            List<Long> customerIds) {

        // 1. 先把指定id的区域的所有客户解绑
        customerDao.unAssignAllCustomersByFixedArea(fixedAreaId);

        // 2. 把右边窗口所有的客户都管关联到指定的区域中
        for (Long cusId : customerIds) {
            customerDao.assignCustomer2FixedArea(fixedAreaId, cusId);
        }

    }

}
