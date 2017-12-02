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

import com.eliefly.bos.domain.base.SubArea;
import com.eliefly.bos.service.base.SubAreaService;
import com.eliefly.bos.web.action.common.CommonAction;

/**
 * ClassName:SubAreaAction <br/>
 * Function: <br/>
 * Date: 2017年12月2日 下午3:18:41 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubAreaAction extends CommonAction<SubArea> {

    private static final long serialVersionUID = -5450540878433431123L;

    public SubAreaAction() {

        super(SubArea.class);
    }

    @Autowired
    private SubAreaService subAreaService;

    /*
     * 分页查询分区
     */
    @Action(value = "subareaAction_pageQuery")
    public String pageQuery() throws IOException {

        // JpaRepository 接口继承的 PagingAndSortingRepository 接口有分页查询规范
        Pageable pageable = new PageRequest(page - 1, rows);

        // 查询的数据封装在 Page 对象中
        Page<SubArea> page = subAreaService.pageQuery(pageable);

        page2json(page, new String[] {"fixedArea", "subareas"});

        return NONE;
    }

    /*
     * 分区保存
     */
    @Action(value = "subareaAction_save", results = {@Result(name = "success",
            location = "/pages/base/sub_area.html", type = "redirect")})
    public String save() {

        subAreaService.save(getModel());
        return SUCCESS;
    }

}
