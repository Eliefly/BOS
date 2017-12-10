package com.eliefly.bos.service.take_delivery;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.eliefly.bos.domain.take_delivery.Order;

/**
 * ClassName:OrderService <br/>
 * Function: <br/>
 * Date: 2017年12月10日 下午4:23:45 <br/>
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
public interface OrderService {

    @POST
    @Path("/saveOrder")
    public void saveOrder(Order order);

}
