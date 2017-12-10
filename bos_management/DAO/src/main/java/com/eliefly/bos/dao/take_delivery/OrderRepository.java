package com.eliefly.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliefly.bos.domain.take_delivery.Order;

/**
 * ClassName:OrderRepository <br/>
 * Function: <br/>
 * Date: 2017年12月10日 下午4:29:55 <br/>
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

}
