package com.eliefly.bos.web.action.base;

import java.io.IOException;
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
import com.eliefly.bos.domain.base.SubArea;
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

    private List<Long> customerIds;

    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }

    private Long courierId;

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    private Long takeTimeId;

    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }

    private List<Long> subAreaIds;

    public void setSubAreaIds(List<Long> subAreaIds) {
        this.subAreaIds = subAreaIds;
    }

    /*
     * 指定分区关联到定区
     */
    @Action(value = "fixedAreaAction_assignSubAreas2FixedArea",
            results = {@Result(name = "success",
                    location = "/pages/base/fixed_area.html",
                    type = "redirect")})
    public String assignSubAreas2FixedArea() throws IOException {

        fixAreaService.assignSubAreas2FixedArea(getModel().getId(), subAreaIds);

        return SUCCESS;
    }

    /*
     * 查询未关联定区的分区
     */
    @Action(value = "fixedAreaAction_findNoassociationSubArea")
    public String findNoassociationSubArea() throws IOException {

        List<SubArea> list = fixAreaService.findNoassociationSubArea();

        list2json(list, new String[] {"area", "fixedArea"});

        return NONE;
    }

    /*
     * 查询已关联定区的分区
     */
    @Action(value = "fixedAreaAction_findAssociationSubArea")
    public String findAssociationSubArea() throws IOException {

        List<SubArea> list =
                fixAreaService.findAssociationSubArea(getModel().getId());

        list2json(list, new String[] {"fixedArea", "subareas"});
        return NONE;
    }

    /*
     * 指定快递员关联到定区, 同时关联取派时间到快递员
     */
    @Action(value = "fixedAreaAction_associationCourierToFixedArea",
            results = {@Result(name = "success",
                    location = "/pages/base/fixed_area.html", type = "redirect")

            })
    public String associationCourierToFixedArea() throws IOException {

        fixAreaService.associationCourierToFixedArea(getModel().getId(),
                courierId, takeTimeId);

        return SUCCESS;
    }

    /*
     * 指定客户关联到定区
     */
    @Action(value = "fixedAreaAction_assignCustomers2FixedArea",
            results = {@Result(name = "success",
                    location = "/pages/base/fixed_area.html", type = "redirect")

            })
    public String assignCustomers2FixedArea() throws IOException {

        WebClient
                .create("http://localhost:8180/crm/webservice/customerService/assignCustomers2FixedArea")
                .accept(MediaType.APPLICATION_JSON)
                .query("fixedAreaId", getModel().getId())
                .query("customerIds", customerIds).put(null);

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
