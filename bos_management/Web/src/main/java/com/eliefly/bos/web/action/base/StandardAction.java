package com.eliefly.bos.web.action.base;

import java.io.IOException;
import java.util.List;

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

import com.eliefly.bos.domain.base.Standard;
import com.eliefly.bos.service.base.StandardService;
import com.eliefly.bos.web.action.common.CommonAction;

/**
 * ClassName:StandardAction <br/>
 * Function: <br/>
 * Date: 2017年11月30日 下午4:26:03 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends CommonAction<Standard> {

    private static final long serialVersionUID = 8298770702184902345L;

    public StandardAction() {
        super(Standard.class);
    }

    @Autowired
    private StandardService standardService;

    /*
     * 查询所有收件标准
     */
    @Action(value = "standardAction_findAll")
    public String findAll() throws IOException {

        List<Standard> list = standardService.findAll();

        list2json(list, null);

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

        page2json(page, null);

        return NONE;
    }

    /*
     * 增加收件标准 & 修改收件标准
     */
    @Action(value = "standardAction_save", results = {@Result(name = "success",
            location = "/pages/base/standard.html", type = "redirect")})
    public String save() {

        standardService.save(getModel());
        return SUCCESS;
    }

}
