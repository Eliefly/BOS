package com.eliefly.test;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.eliefly.bos.domain.take_delivery.Order;

/**
 * ClassName:ManagementWebServiceTest <br/>
 * Function: <br/>
 * Date: 2017年12月10日 下午4:43:08 <br/>
 */

public class ManagementWebServiceTest {

    @Test
    public void test01() {

        Order order = new Order();

        order.setSendAddress("111111");

        WebClient
                .create("http://localhost:8080/Web/webservice/orderService/saveOrder")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).post(order);

    }

}
