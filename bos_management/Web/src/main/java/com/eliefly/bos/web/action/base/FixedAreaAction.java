package com.eliefly.bos.web.action.base;

import java.io.IOException;

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

import com.eliefly.bos.domain.base.FixedArea;
import com.eliefly.bos.service.base.FixAreaService;
import com.eliefly.bos.web.action.common.CommonAction;

/**
 * ClassName:FixedAreaAction <br/>
 * Function: <br/>
 * Date: 2017年12月2日 下午8:09:38 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {

    private static final long serialVersionUID = 854485380830937339L;

    public FixedAreaAction() {

        super(FixedArea.class);
    }

    @Autowired
    private FixAreaService fixAreaService;

    /*
     * 分页查询定区
     */
    @Action(value = "fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException {

        // JpaRepository 接口继承的 PagingAndSortingRepository 接口有分页查询规范
        Pageable pageable = new PageRequest(page - 1, rows);

        // 查询的数据封装在 Page 对象中
        Page<FixedArea> page = fixAreaService.pageQuery(pageable);

        page2json(page, new String[] {"operatingTime", "operator",
                "operatingCompany", "subareas", "couriers"});

        return NONE;
    }

    /*
     * 定区保存
     */
    @Action(value = "fixedAreaAction_save",
            results = {@Result(name = "success",
                    location = "/pages/base/fixed_area.html",
                    type = "redirect")})
    public String save() {

        fixAreaService.save(getModel());
        return SUCCESS;
    }

}
