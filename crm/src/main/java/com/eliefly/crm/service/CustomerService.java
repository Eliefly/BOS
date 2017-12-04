package com.eliefly.crm.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.eliefly.crm.domain.Customer;

/**
 * ClassName:CustomerService <br/>
 * Function: <br/>
 * Date: 2017年12月3日 下午5:06:02 <br/>
 */
public interface CustomerService {

    @GET
    @Path("/crm")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<Customer> findAll();

    @GET
    @Path("/noassociationCustomer")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<Customer> findNoassociationCustomer();

    @GET
    @Path("/associationCustomer")
    @QueryParam("fixedAreaId")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<Customer> findAssociationCustomer(
            @QueryParam("fixedAreaId") String fixedAreaId);

    @PUT
    @Path("/assignCustomers2FixedArea")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public void assignCustomers2FixedArea(
            @QueryParam("fixedAreaId") String fixedAreaId,
            @QueryParam("customerIds") List<Long> customerIds);

}
