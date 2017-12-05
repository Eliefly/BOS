package com.elifly.test;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.eliefly.crm.domain.Customer;

/**
 * ClassName:CrmWebServiceTest <br/>
 * Function: <br/>
 * Date: 2017年12月3日 下午5:33:05 <br/>
 */
public class CrmWebServiceTest {

    @Test
    public void test01() {
        Collection<? extends Customer> collection = WebClient
                .create("http://localhost:8180/crm/webservice/customerService/findAllCustomers")
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);

        for (Customer customer : collection) {
            System.out.println(customer);
        }

    }

    @Test
    public void test02() {

        List<? extends Customer> list = (List<? extends Customer>) WebClient
                .create("http://localhost:8180/crm/webservice/customerService/noassociationCustomer")
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);

        for (Customer customer : list) {
            System.out.println(customer);
        }

    }

    @Test
    public void test03() {

        Collection<? extends Customer> collection = WebClient
                .create("http://localhost:8180/crm/webservice/customerService/associationCustomer")
                .query("fixedAreaId", "82").accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);

        for (Customer customer : collection) {
            System.out.println(customer);
        }

    }

}
