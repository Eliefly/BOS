package com.eliefly.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

    // 查询所有客户
    @GET
    @Path("/findAllCustomers")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<Customer> findAll();

    // 查询为关联任何定区的客户
    @GET
    @Path("/noassociationCustomer")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<Customer> findNoassociationCustomer();

    // 查询关联到当前定区的客户
    @GET
    @Path("/associationCustomer")
    @QueryParam("fixedAreaId")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<Customer> findAssociationCustomer(
            @QueryParam("fixedAreaId") String fixedAreaId);

    // 关联指定的客户到到当前定区
    @PUT
    @Path("/assignCustomers2FixedArea")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public void assignCustomers2FixedArea(
            @QueryParam("fixedAreaId") String fixedAreaId,
            @QueryParam("customerIds") List<Long> customerIds);

    // 注册客户
    @POST
    @Path("/registerCustomer")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public void registerCustomer(Customer customer);

    // 通过 Telephone 查询客户
    @GET
    @Path("/findByTelephone")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Customer findByTelephone(@QueryParam("telephone") String telephone);

    // 激活客户
    @PUT
    @Path("/activeCustomer")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    public void activeCustomer(@QueryParam("telephone") String telephone);

    // 登录
    @GET
    @Path("/login")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Customer findByTelephoneAndPassword(@QueryParam("telephone") String telephone,
            @QueryParam("password") String password);

}
