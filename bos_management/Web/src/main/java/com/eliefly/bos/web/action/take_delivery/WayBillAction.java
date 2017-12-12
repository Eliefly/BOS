package com.eliefly.bos.web.action.take_delivery;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.eliefly.bos.domain.take_delivery.WayBill;
import com.eliefly.bos.service.take_delivery.WayBillService;
import com.eliefly.bos.web.action.common.CommonAction;

/**
 * ClassName:WaybillAction <br/>
 * Function: <br/>
 * Date: 2017年12月12日 下午5:17:09 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class WayBillAction extends CommonAction<WayBill> {

    private static final long serialVersionUID = -7572342059101395968L;

    @Autowired
    private WayBillService wayBillService;

    public WayBillAction() {

        super(WayBill.class);

    }

    // waybillAction_save

    @Action(value = "waybillAction_save",
            results = {@Result(name = "success",
                    location = "/pages/take_delivery/waybill_quick.html",
                    type = "redirect")})
    public String save() throws IOException {

        // WayBill model2 = getModel();

        String flag = "1";

        try {
            wayBillService.save(getModel());
        } catch (Exception e) {

            e.printStackTrace();
            //保存运单失败
            flag = "0";
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print(flag);

        return SUCCESS;
    }

}
