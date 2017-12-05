package com.eliefly.test;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.eliefly.crm.domain.Customer;

/**
 * ClassName:CrmWebServiceTest <br/>
 * Function: <br/>
 * Date: 2017年12月5日 下午7:40:37 <br/>
 */
public class CrmWebServiceTest {

    @Test
    public void test01() {

        Customer customer = new Customer();

        customer.setId(124L);
        customer.setAddress("深圳");

        WebClient
                .create("http://localhost:8180/crm/webservice/customerService/registerCustomer")
                .type(MediaType.APPLICATION_JSON).post(customer);
    }

}
