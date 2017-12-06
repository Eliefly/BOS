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

    /*
     * 查找所有的客户
     */
    @Override
    public List<Customer> findAll() {

        return customerDao.findAll();
    }

    /*
     * 查找还未关联任何定区的客户
     */
    @Override
    public List<Customer> findNoassociationCustomer() {

        return customerDao.findNoassociationCustomer();
    }

    /*
     * 查找指定定区上关联的客户
     */
    @Override
    public List<Customer> findAssociationCustomer(String id) {

        return customerDao.findAssociationCustomer(id);
    }

    /*
     * 把指定客户关联到指定的定区
     */
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

    /*
     * 插入客户
     */
    @Override
    public void registerCustomer(Customer customer) {

        customerDao.save(customer);
    }

    /*
     * 通过 Telephone 查询客户
     */
    @Override
    public Customer findByTelephone(String telephone) {

        return customerDao.findByTelephone(telephone);
    }

    /*
     * 激活客户
     */
    @Override
    public void activeCustomer(String telephone) {

        customerDao.activeCustomer(telephone);
    }

    @Override
    public Customer findByTelephoneAndPassword(String telephone, String password) {

        return customerDao.findByTelephoneAndPassword(telephone, password);
    }

}
