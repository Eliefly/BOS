package com.eliefly.bos.web.action.system;

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

import com.eliefly.bos.domain.system.Permission;
import com.eliefly.bos.service.system.PermissionService;
import com.eliefly.bos.web.action.common.CommonAction;

/**
 * ClassName:PermissionAction <br/>
 * Function: <br/>
 * Date: 2017年12月14日 下午3:51:11 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PermissionAction extends CommonAction<Permission> {

    private static final long serialVersionUID = -638023506361223316L;

    @Autowired
    private PermissionService permissionService;

    public PermissionAction() {

        super(Permission.class);
    }

    /*
     * 查询所有权限
     */
    @Action(value = "permissionAction_findAll")
    public String findAll() throws IOException {

        List<Permission> list = permissionService.findAll();

        list2json(list, new String[] {"roles", "keyword", "description"});

        return NONE;
    }

    /*
     * 分页查询权限
     */
    @Action(value = "permissionAction_pageQuery")
    public String pageQuery() throws IOException {

        // JpaRepository 接口继承的 PagingAndSortingRepository 接口有分页查询规范
        // EasyUI的页码从1开始,SpringDataJPA框架构造PageRequest的时候,page是从0开始的
        Pageable pageable = new PageRequest(page - 1, rows);

        // 查询的数据封装在 Page 对象中
        Page<Permission> page = permissionService.pageQuery(pageable);

        page2json(page, new String[] {"roles"});

        return NONE;
    }

    /*
     * 保存权限
     */
    @Action(value = "permissionAction_save",
            results = {@Result(name = "success",
                    location = "/pages/system/permission.html",
                    type = "redirect")})
    public String save() {

        permissionService.save(getModel());
        return SUCCESS;
    }

}
