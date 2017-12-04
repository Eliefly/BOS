package com.eliefly.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.eliefly.bos.domain.base.FixedArea;
import com.eliefly.bos.service.base.FixAreaService;
import com.eliefly.bos.web.action.common.CommonAction;
import com.eliefly.crm.domain.Customer;

/**
 * ClassName:FixedAreaAction <br/>
 * Function: <br/>
 * Date: 2017年12月2日 下午8:09:38 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {

    private static final long serialVersionUID = 854485380830937339L;

    public FixedAreaAction() {

        super(FixedArea.class);
    }

    @Autowired
    private FixAreaService fixAreaService;

    private String[] customerIds;

    public void setCustomerIds(String[] customerIds) {
        this.customerIds = customerIds;
    }

    /*
     * 定区关联客户
     */
    @Action(value = "fixedAreaAction_assignCustomers2FixedArea",
            results = {@Result(name = "success",
                    location = "/pages/base/fixed_area.html", type = "redirect")

            })
    public String assignCustomers2FixedArea() throws IOException {

        ArrayList<Long> list = new ArrayList<Long>();

        for (String cusId : customerIds) {
            list.add(Long.parseLong(cusId));
        }

        WebClient
                .create("http://localhost:8180/crm/webservice/customerService/assignCustomers2FixedArea")
                .accept(MediaType.APPLICATION_JSON)
                .query("fixedAreaId", getModel().getId())
                .query("customerIds", list).put(null);

        return SUCCESS;
    }

    /*
     * 查询未关联定区的账户
     */
    @Action(value = "fixedAreaAction_findNoassociationCustomer")
    public String findNoassociationCustomer() throws IOException {

        List<? extends Customer> list = (List<? extends Customer>) WebClient
                .create("http://localhost:8180/crm/webservice/customerService/noassociationCustomer")
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);

        list2json(list, null);

        return NONE;
    }

    /*
     * 查询已关联定区的账户
     */
    @Action(value = "fixedAreaAction_findAssociationCustomer")
    public String findAssociationCustomer() throws IOException {

        List<? extends Customer> list = (List<? extends Customer>) WebClient
                .create("http://localhost:8180/crm/webservice/customerService/associationCustomer")
                .query("fixedAreaId", getModel().getId())
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);

        list2json(list, null);
        return NONE;
    }

    /*
     * 分页查询定区
     */
    @Action(value = "fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException {

        // JpaRepository 接口继承的 PagingAndSortingRepository 接口有分页查询规范
        Pageable pageable = new PageRequest(page - 1, rows);

        // 查询的数据封装在 Page 对象中
        Page<FixedArea> page = fixAreaService.pageQuery(pageable);

        page2json(page, new String[] {"operatingTime", "operator",
                "operatingCompany", "subareas", "couriers"});

        return NONE;
    }

    /*
     * 定区保存
     */
    @Action(value = "fixedAreaAction_save",
            results = {@Result(name = "success",
                    location = "/pages/base/fixed_area.html",
                    type = "redirect")})
    public String save() {

        fixAreaService.save(getModel());
        return SUCCESS;
    }

}
