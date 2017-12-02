package com.eliefly.bos.web.action.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONArray;
import com.eliefly.bos.domain.base.Standard;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:CommonAction <br/>
 * Function: <br/>
 * Date: 2017年12月2日 上午8:29:11 <br/>
 * 
 * @param <T>
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {

    private static final long serialVersionUID = -4517251850600031242L;

    private T model;

    protected int page;
    protected int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @SuppressWarnings("unchecked")
    public CommonAction(Class<?> clazz) {
        try {
            model = (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T getModel() {
        return model;
    }

    protected void page2json(Page<T> page, String[] excludes)
            throws IOException {

        List<T> list = page.getContent();
        long totalElements = page.getTotalElements();

        Map<String, Object> map = new HashMap<>();

        map.put("total", totalElements);
        map.put("rows", list);

        // 设置在生成json字符串的时候, 忽略的字段
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);

        // Map 对象转成 JSON
        String json = JSONObject.fromObject(map, jsonConfig).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().println(json);
    }

    protected void list2json(List<Standard> list) throws IOException {
        String json = JSONArray.toJSON(list).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        // 返回 json 格式字符串
        response.getWriter().println(json);
    }

}
