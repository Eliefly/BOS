package com.eliefly.bos.fore.web.action;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.eliefly.crm.domain.Customer;
import com.eliefly.utils.MailUtils;
import com.eliefly.utils.SmsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ClassName:CustomerAction <br/>
 * Function: <br/>
 * Date: 2017年12月5日 下午5:43:55 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport
        implements ModelDriven<Customer> {

    private static final long serialVersionUID = 3772654510216155458L;

    private Customer model;

    private String checkcode;

    private String activeCode;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 属性驱动获取注册验证码
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    // 属性驱动获取客户激活验证码
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    @Override
    public Customer getModel() {
        if (model == null) {
            model = new Customer();
        }

        return model;
    }

    /*
     * 发送手机注册验证码
     */
    @Action(value = "customerAction_sendSMS")
    public String sendSMS() throws UnsupportedEncodingException {

        // 生成随机验证码
        String serverCode = RandomStringUtils.randomNumeric(4);

        // 验证码存放到session中
        ServletActionContext.getRequest().getSession()
                .setAttribute("serverCode", serverCode);

        System.out.println("serverCode: " + serverCode);

        SmsUtils.sendSmsByHTTP(model.getTelephone(),
                "尊敬的客户你好，您本次获取的验证码为：" + serverCode);

        return NONE;
    }

    /*
     * 用户注册
     */
    @Action(value = "customerAction_regist",
            results = {
                    @Result(name = "success", location = "/signup-success.html",
                            type = "redirect"),
                    @Result(name = "error", location = "signup-fail.html",
                            type = "redirect")})
    public String regist() {

        String serverCode = (String) ServletActionContext.getRequest()
                .getSession().getAttribute("serverCode");

        // 如果验证码正确
        if (StringUtils.isNoneEmpty(serverCode)
                && StringUtils.isNoneEmpty(checkcode)
                && serverCode.equals(checkcode)) {

            // TODO 注册之前应该判断该手机是否已经注册

            // 发起 WebService 注册 Customer
            WebClient
                    .create("http://localhost:8180/crm/webservice/customerService/registerCustomer")
                    .type(MediaType.APPLICATION_JSON).post(model);

            // 产生激活码
            String activeCode = RandomStringUtils.randomNumeric(32);

            // 激活码放入 Redis中, 有效期 24 小时
            redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 1,
                    TimeUnit.DAYS);

            // 发送一封激活邮件
            String emailBody =
                    "感谢您注册速运快递,请点击<a href='http://localhost:8280/bos_fore/customerAction_active.action?activeCode="
                            + activeCode + "&telephone=" + model.getTelephone()
                            + "'>本链接</a>,激活您的帐号";

            MailUtils.sendMail(model.getEmail(), "速运快递激活邮件", emailBody);

            return SUCCESS;
        }

        return ERROR;
    }

    /*
     * 用户激活
     */
    @Action(value = "customerAction_active",
            results = {
                    @Result(name = "actived", location = "/active-success.html",
                            type = "redirect"),
                    @Result(name = "success", location = "/active-success.html",
                            type = "redirect"),
                    @Result(name = "error", location = "/active-fail.html",
                            type = "redirect")})
    public String active() {

        // 获取 redis 中的激活码
        String serverActiveCode =
                redisTemplate.opsForValue().get(model.getTelephone());

        // 验证激活码
        if (StringUtils.isNoneEmpty(activeCode)
                && StringUtils.isNoneEmpty(serverActiveCode)
                && activeCode.equals(serverActiveCode)) {

            // 调用 crm webservice 查询客户
            Customer customer = WebClient
                    .create("http://localhost:8180/crm/webservice/customerService/findByTelephone")
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .query("telephone", model.getTelephone())
                    .get(Customer.class);

            if (customer != null && customer.getType() != null
                    && customer.getType() == 1) {
                // 用户已激活, 直接返回
                return "actived";
            }

            // 调用 crm webservice 激活客户
            WebClient
                    .create("http://localhost:8180/crm/webservice/customerService/activeCustomer")
                    .type(MediaType.APPLICATION_JSON)
                    .query("telephone", "18576725089").put(null);

            return SUCCESS;

        }

        return ERROR;
    }

    /*
     * 客户登录
     */
    @Action(value = "customerAction_login",
            results = {
                    @Result(name = "success", location = "/index.html",
                            type = "redirect"),
                    @Result(name = "need_active", location = "/need-active.html",
                            type = "redirect"),
                    @Result(name = "error", location = "/login-fail.html",
                            type = "redirect")})
    public String login() {

        String validateCode = (String) ServletActionContext.getRequest()
                .getSession().getAttribute("validateCode");

        if (StringUtils.isNoneEmpty(checkcode)
                && StringUtils.isNotEmpty("validateCode")
                && checkcode.equals(validateCode)) {

            Customer customer = WebClient
                    .create("http://localhost:8180/crm/webservice/customerService/login")
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .query("telephone", model.getTelephone())
                    .query("password", model.getPassword()).get(Customer.class);
            // 存在客户
            if (customer != null) {
                // 客户已激活
                if (customer.getType() != null && customer.getType() == 1) {
                    ServletActionContext.getRequest().getSession()
                            .setAttribute("customer", customer);

                    return SUCCESS;
                }

                return "need_active";

            }

        }

        return ERROR;
    }

}
