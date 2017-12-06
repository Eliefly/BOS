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

    @Test
    public void test02() {

        Customer customer = WebClient
                .create("http://localhost:8180/crm/webservice/customerService/findByTelephone")
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .query("telephone", "15083903374").get(Customer.class);

        System.out.println(customer);
    }

    @Test
    public void test03() {

        WebClient
                .create("http://localhost:8180/crm/webservice/customerService/activeCustomer")
                .type(MediaType.APPLICATION_JSON)
                .query("telephone", "15083903374").put(null);

    }

    @Test
    public void test04() {

        Customer customer = WebClient
                .create("http://localhost:8180/crm/webservice/customerService/login")
                .accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_JSON)
                .query("telephone", "15083903374").query("password", "123456")
                .get(Customer.class);

        System.out.println(customer);
    }

}
