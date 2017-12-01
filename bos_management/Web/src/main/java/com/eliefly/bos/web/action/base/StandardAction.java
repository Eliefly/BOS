package com.eliefly.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eliefly.bos.domain.base.Standard;
import com.eliefly.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ClassName:StandardAction <br/>
 * Function: <br/>
 * Date: 2017年11月30日 下午4:26:03 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport
        implements ModelDriven<Standard> {

    private static final long serialVersionUID = 8298770702184902345L;

    @Autowired
    private StandardService standardService;

    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    private Standard model;

    @Override
    public Standard getModel() {

        if (model == null) {
            model = new Standard();
        }
        return model;
    }

    /*
     * 查询所有收件标准
     */
    @Action(value = "standardAction_findAll")
    public String findAll() throws IOException {

        List<Standard> list = standardService.findAll();
        String json = JSONArray.toJSON(list).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        // 返回 json 格式字符串
        response.getWriter().println(json);

        return NONE;
    }

    /*
     * 分页查询收件标准
     */
    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws IOException {

        // JpaRepository 接口继承的 PagingAndSortingRepository 接口有分页查询规范
        // EasyUI的页码从1开始,SpringDataJPA框架构造PageRequest的时候,page是从0开始的
        Pageable pageable = new PageRequest(page - 1, rows);

        // 查询的数据封装在 Page 对象中
        Page<Standard> page = standardService.pageQuery(pageable);

        List<Standard> list = page.getContent();
        long totalElements = page.getTotalElements();

        Map<String, Object> map = new HashMap<>();

        map.put("total", totalElements);
        map.put("rows", list);

        String json = JSONObject.toJSON(map).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");

        // 返回 json 格式字符串
        response.getWriter().println(json);

        return NONE;
    }

    /*
     * 增加收件标准 & 修改收件标准
     */
    @Action(value = "standardAction_save", results = {@Result(name = "success",
            location = "/pages/base/standard.html", type = "redirect")})
    public String save() {

        standardService.save(model);
        return SUCCESS;
    }

}
