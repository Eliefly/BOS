package com.eliefly.bos.web.action.system;

import java.io.IOException;
import java.util.List;

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

import com.eliefly.bos.domain.system.Menu;
import com.eliefly.bos.domain.system.User;
import com.eliefly.bos.service.system.MenuService;
import com.eliefly.bos.web.action.common.CommonAction;

/**
 * ClassName:MenuAction <br/>
 * Function: <br/>
 * Date: 2017年12月14日 上午11:25:10 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class MenuAction extends CommonAction<Menu> {

    private static final long serialVersionUID = 7977144438727233257L;

    @Autowired
    private MenuService menuService;

    public MenuAction() {

        super(Menu.class);
    }

    /*
     * 动态加载菜单
     */
    @Action(value = "menuAction_findByUser")
    public String findByUser() throws IOException {

        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");

        List<Menu> list = menuService.findByUser(user);

        System.out.println(list);

        list2json(list, new String[] {"roles", "parentMenu", "childrenMenus", "children"});

        return NONE;
    }

    /*
     * 菜单分页查询
     */
    @Action(value = "menuAction_pageQuery")
    public String pageQuery() throws IOException {

        // JpaRepository 接口继承的 PagingAndSortingRepository 接口有分页查询规范
        // EasyUI的页码从1开始,SpringDataJPA框架构造PageRequest的时候,page是从0开始的
        Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage()) - 1, rows);

        // 查询的数据封装在 Page 对象中
        Page<Menu> page = menuService.pageQuery(pageable);

        page2json(page, new String[] {"roles", "parentMenu", "childrenMenus"});

        return NONE;
    }

    /*
     * 保存菜单
     */
    @Action(value = "menuAction_save", results = {
            @Result(name = "success", location = "/pages/system/menu.html", type = "redirect")})
    public String save() {

        // 添加一级菜单: 父id为空
        if (getModel().getParentMenu() != null && getModel().getParentMenu().getId() == null) {

            getModel().setParentMenu(null);
        }

        menuService.save(getModel());
        return SUCCESS;
    }

    /*
     * 查找所有父菜单项
     */
    @Action(value = "menuAction_findAllTopMenus")
    public String findAllTopMenus() throws IOException {

        List<Menu> list = menuService.findByParentMenuIsNull();

        list2json(list, new String[] {"roles", "parentMenu", "childrenMenus", "priority",
                "description", "page"});

        return NONE;
    }

    /*
     * 根据角色ID查询菜单
     */
    @Action("menuAction_findByRoleId")
    public String findByRoleId() throws IOException {

        List<Menu> list = menuService.findByRoleId(getModel().getId());

        list2json(list, new String[] {"roles", "childrenMenus", "parentMenu", "children"});

        return NONE;
    }

    /*
     * 查护所有菜单
     */
    @Action(value = "menuAction_findAll")
    public String findAll() throws IOException {

        List<Menu> list = menuService.findAll();

        list2json(list, new String[] {"roles", "childrenMenus", "parentMenu", "children"});

        return NONE;
    }

}
