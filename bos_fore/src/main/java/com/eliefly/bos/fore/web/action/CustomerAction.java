package com.eliefly.bos.fore.web.action;

import java.io.UnsupportedEncodingException;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.eliefly.crm.domain.Customer;
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

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Override
    public Customer getModel() {
        if (model == null) {
            model = new Customer();
        }

        return model;
    }

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

    @Action(value = "customerAction_regist",
            results = {
                    @Result(name = "success", location = "/signup-success.html",
                            type = "redirect"),
                    @Result(name = "error", location = "signup-fail.html",
                            type = "redirect")})
    public String regist() {

        String serverCode = (String) ServletActionContext.getRequest()
                .getSession().getAttribute("serverCode");

        System.out.println(serverCode);
        System.out.println(checkcode);

        // 如果验证码正确
        if (StringUtils.isNoneEmpty(serverCode)
                && StringUtils.isNoneEmpty(checkcode)
                && serverCode.equals(checkcode)) {

            WebClient
                    .create("http://localhost:8180/crm/webservice/customerService/registerCustomer")
                    .type(MediaType.APPLICATION_JSON).post(model);

            return SUCCESS;
        }

        return ERROR;
    }

}
