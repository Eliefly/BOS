package com.eliefly.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.eliefly.bos.domain.base.TakeTime;
import com.eliefly.bos.service.base.TakeTimeService;
import com.eliefly.bos.web.action.common.CommonAction;

/**
 * ClassName:TakeTimeAction <br/>
 * Function: <br/>
 * Date: 2017年12月5日 上午11:54:31 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends CommonAction<TakeTime> {

    private static final long serialVersionUID = -7852084520888210555L;

    @Autowired
    private TakeTimeService takeTimeService;

    public TakeTimeAction() {

        super(TakeTime.class);
    }

    /*
     * 查询收派时间
     */
    @Action(value = "takeTimeAction_listajax")
    public String listajax() throws IOException {

        List<TakeTime> list = takeTimeService.findAll();

        list2json(list, null);

        return NONE;
    }

}
