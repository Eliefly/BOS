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

import com.eliefly.bos.domain.system.Role;
import com.eliefly.bos.service.system.RoleService;
import com.eliefly.bos.web.action.common.CommonAction;

/**
 * ClassName:RoleAction <br/>
 * Function: <br/>
 * Date: 2017年12月15日 下午2:55:13 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class RoleAction extends CommonAction<Role> {

    private static final long serialVersionUID = -4538978885765841445L;

    @Autowired
    private RoleService roleService;

    private List<Long> permissionIds;

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    private String menuIds;

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public RoleAction() {

        super(Role.class);
    }

    /*
     * 保存角色
     */
    @Action(value = "roleAction_save", results = {
            @Result(name = "success", location = "/pages/system/role.html", type = "redirect")})
    public String save() {

        roleService.save(getModel(), menuIds, permissionIds);

        return SUCCESS;
    }

    /*
     * 查询所有角色
     */
    @Action(value = "roleAction_findAll")
    public String findAll() throws IOException {

        List<Role> list = roleService.findAll();

        list2json(list, new String[] {"permissions", "menus", "users"});

        return NONE;
    }

    /*
     * 角色分页查询
     */
    @Action(value = "roleAction_pageQuery")
    public String pageQuery() throws IOException {

        // JpaRepository 接口继承的 PagingAndSortingRepository 接口有分页查询规范
        // EasyUI的页码从1开始,SpringDataJPA框架构造PageRequest的时候,page是从0开始的
        Pageable pageable = new PageRequest(page - 1, rows);

        // 查询的数据封装在 Page 对象中
        Page<Role> page = roleService.pageQuery(pageable);

        page2json(page, new String[] {"users", "permissions", "menus"});

        return NONE;
    }

}
