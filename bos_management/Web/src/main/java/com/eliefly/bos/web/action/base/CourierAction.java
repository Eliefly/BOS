package com.eliefly.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.eliefly.bos.domain.base.Courier;
import com.eliefly.bos.domain.base.Standard;
import com.eliefly.bos.service.base.CourierService;
import com.eliefly.bos.web.action.common.CommonAction;

/**
 * ClassName:CourierAction <br/>
 * Function: <br/>
 * Date: 2017年12月1日 上午10:15:01 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends CommonAction<Courier> {

    private static final long serialVersionUID = 9104033846060074565L;

    public CourierAction() {
        super(Courier.class);
    }

    // 删除参数
    private String ids;

    @Autowired
    private CourierService courierService;

    public void setIds(String ids) {
        this.ids = ids;
    }

    // courierAction_listajax.action

    /*
     * 查询所有快递员
     */
    @Action(value = "courierAction_listajax")
    public String listajax() throws IOException {

        List<Courier> list = courierService.findAll();

        list2json(list, new String[] {"standard", "fixedAreas", "takeTime"});

        return NONE;
    }

    /*
     * 删除快递员(将删除标志位置'1')
     */
    @Action(value = "courierAction_delete", results = {@Result(name = "success",
            location = "pages/base/courier.html", type = "redirect")})
    public String delete() {

        courierService.delete(ids);
        return SUCCESS;
    }

    /*
     * 分页查询快递员
     */
    @Action(value = "courierAction_pageQuery")
    public String pageQuery() throws IOException {

        // 动态创建查询条件
        Specification<Courier> spec = new Specification<Courier>() {

            // 获取条件查询参数
            String courierNum = getModel().getCourierNum();
            Standard standard = getModel().getStandard();
            String company = getModel().getCompany();
            String type = getModel().getType();

            // List 存放查询条件
            List<Predicate> list = new ArrayList<>();

            // 在这个方法中构造查询条件
            // root : 根对象,一般可以直接理解为泛型对象.以本例来说,root可以简单的理解为就是Courier对象
            // cb : 用来构造查询条件的对象
            @Override
            public Predicate toPredicate(Root<Courier> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {

                // 查询参数 courierNum 部位空
                if (StringUtils.isNotEmpty(courierNum)) {
                    Predicate p1 =
                            cb.equal(root.get("courierNum").as(String.class),
                                    courierNum);
                    // 添加查询条件
                    list.add(p1);
                }

                if (standard != null) {

                    String name = standard.getName();

                    if (StringUtils.isNotEmpty(name)) {

                        // 注意：这里用的root.join 因为我们要用 standard 对象里的字段作为条件. 在Courier中standard是多对一的关联字段.
                        Predicate p2 = cb.like(root.join("standard").get("name")
                                .as(String.class), "%" + name + "%");
                        list.add(p2);
                    }

                }

                if (StringUtils.isNotEmpty(company)) {
                    Predicate p3 = cb.like(root.get("company").as(String.class),
                            "%" + company + "%");
                    list.add(p3);
                }

                if (StringUtils.isNoneEmpty(type)) {
                    Predicate p4 =
                            cb.equal(root.get("type").as(String.class), type);
                    list.add(p4);
                }

                // 如果所有输入条件都为空
                if (list.size() == 0) {
                    return null;
                }

                // 构建最终的 Predicate, p1, p2, p3, p4 的条件是与关系, 所以直接使用数组. 更复杂的关系如:
                // cb.and(p3,cb.or(p1,p2))

                // 构建多个and条件的时候, 需要使用数组. 所以要把list集合转为数组
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                // 构造and查询条件

                return cb.and(arr);
            }
        };

        Pageable pageable = new PageRequest(page - 1, rows);

        Page<Courier> page = courierService.findAll(spec, pageable);

        page2json(page, new String[] {"fixedAreas", "takeTime"});

        return NONE;
    }

    /*
     * 保存快递员
     */
    @Action(value = "courierAction_save", results = {@Result(name = "success",
            location = "pages/base/courier.html", type = "redirect")})
    public String save() {

        courierService.save(getModel());
        return SUCCESS;
    }

}
