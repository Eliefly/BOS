package com.eliefly.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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

import com.eliefly.bos.domain.system.User;
import com.eliefly.bos.service.system.UserService;
import com.eliefly.bos.web.action.common.CommonAction;

/**
 * ClassName:UserAction <br/>
 * Function: <br/>
 * Date: 2017年12月12日 下午9:19:16 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class UserAction extends CommonAction<User> {

    private static final long serialVersionUID = -1153323559389029967L;

    private String checkCode;

    @Autowired
    private UserService userService;

    private List<Long> roleIds;

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public UserAction() {

        super(User.class);
    }

    // userAction_pageQuery

    /*
     * 用户分页查询
     */
    @Action(value = "userAction_pageQuery")
    public String pageQuery() throws IOException {

        // JpaRepository 接口继承的 PagingAndSortingRepository 接口有分页查询规范
        // EasyUI的页码从1开始,SpringDataJPA框架构造PageRequest的时候,page是从0开始的
        Pageable pageable = new PageRequest(page - 1, rows);

        // 查询的数据封装在 Page 对象中
        Page<User> page = userService.pageQuery(pageable);

        page2json(page, new String[] {"roles"});

        return NONE;
    }

    /*
     * 保存用户
     */
    @Action(value = "userAction_save", results = {
            @Result(name = "success", location = "/pages/system/userlist.html", type = "redirect")})
    public String save() {

        userService.save(getModel(), roleIds);

        return SUCCESS;
    }

    /*
     * 用户注销
     */
    @Action(value = "userAction_logout",
            results = {@Result(name = "login", location = "/login.jsp", type = "redirect")})
    public String logout() {

        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return LOGIN;
    }

    /*
     * 用户登录
     */
    @Action(value = "userAction_login",
            results = {@Result(name = "success", location = "/index.html", type = "redirect"),
                    @Result(name = "login", location = "/login.jsp", type = "dispatcher")})
    public String login() {

        String validateCode = (String) ServletActionContext.getRequest().getSession()
                .getAttribute("validateCode");

        // System.out.println("登录验证....");

        if (StringUtils.isNoneEmpty(checkCode) && StringUtils.isNoneEmpty(validateCode)
                && checkCode.equalsIgnoreCase(validateCode)) {

            // 登录交给 shiro 框架处理
            Subject subject = SecurityUtils.getSubject();

            // 创建令牌
            UsernamePasswordToken token =
                    new UsernamePasswordToken(getModel().getUsername(), getModel().getPassword());

            // 登录
            try {
                // 如果无异常则登录成功
                subject.login(token);

                User user = (User) subject.getPrincipal();
                ServletActionContext.getRequest().getSession().setAttribute("user", user);

                return SUCCESS;
            } catch (UnknownAccountException e) {
                // addActionError("用户不存在.");
                ServletActionContext.getRequest().setAttribute("msg", "用户不存在.");

            } catch (IncorrectCredentialsException e) {

                // addActionError("密码错误.");
                ServletActionContext.getRequest().setAttribute("msg", "密码错误.");
            }
        } else {

            // addActionError("验证码错误.");
            ServletActionContext.getRequest().setAttribute("msg", "验证码错误.");
        }

        return LOGIN;
    }

}
