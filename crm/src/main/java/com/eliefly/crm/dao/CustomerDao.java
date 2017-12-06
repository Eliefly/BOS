package com.eliefly.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.eliefly.crm.domain.Customer;

/**
 * ClassName:CustomerDao <br/>
 * Function: <br/>
 * Date: 2017年12月3日 下午5:21:00 <br/>
 */
public interface CustomerDao extends JpaRepository<Customer, Long> {

    @Query(value = "from Customer where fixedAreaId is null")
    List<Customer> findNoassociationCustomer();

    @Query(value = "from Customer where fixedAreaId = ?")
    List<Customer> findAssociationCustomer(String id);

    @Modifying
    @Query(value = "update Customer set fixedAreaId = null where fixedAreaId = ?")
    void unAssignAllCustomersByFixedArea(String fixedAreaId);

    @Modifying
    @Query(value = "update Customer set fixedAreaId = ? where id = ?")
    void assignCustomer2FixedArea(String fixedAreaId, Long cusId);

    Customer findByTelephone(String telephone);

    @Modifying
    @Query(value = "update Customer set type = 1 where telephone = ?")
    void activeCustomer(String telephone);

    @Query("from Customer where telephone = ? and password = ?")
    Customer findByTelephoneAndPassword(String telephone, String password);

}
