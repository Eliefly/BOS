package com.eliefly.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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

import com.eliefly.bos.domain.base.Area;
import com.eliefly.bos.service.base.AreaService;
import com.eliefly.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:AreaAction <br/>
 * Function: <br/>
 * Date: 2017年12月1日 下午9:55:18 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends ActionSupport implements ModelDriven<Area> {

    private static final long serialVersionUID = -7558456496886326233L;

    // 使用 jquery.ocupload-1.1.2.js 上传excel文件
    private File file;

    private Area model;

    @Autowired
    private AreaService areaService;

    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public Area getModel() {
        if (model == null) {
            model = new Area();
        }
        return model;
    }

    /*
     * 分页查询区域
     */
    @Action(value = "areaAction_pageQuery")
    public String pageQuery() throws IOException {

        // JpaRepository 接口继承的 PagingAndSortingRepository 接口有分页查询规范
        // EasyUI的页码从1开始,SpringDataJPA框架构造PageRequest的时候,page是从0开始的
        Pageable pageable = new PageRequest(page - 1, rows);

        // 查询的数据封装在 Page 对象中
        Page<Area> page = areaService.pageQuery(pageable);

        List<Area> list = page.getContent();
        long totalElements = page.getTotalElements();

        Map<String, Object> map = new HashMap<>();

        map.put("total", totalElements);
        map.put("rows", list);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        String json = JSONObject.fromObject(map, jsonConfig).toString();

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");

        // 返回 json 格式字符串
        response.getWriter().println(json);

        return NONE;
    }

    /*
     * excel文件导入区域数据
     */
    @Action(value = "areaAction_importXSL", results = {@Result(name = "success",
            location = "/pages/base/area.html", type = "redirect")})
    public String importXSL() throws Exception {

        // String absolutePath = file.getAbsolutePath();
        // System.out.println(absolutePath);

        // 创建个workbook，根据POIFSFileSystem对象
        HSSFWorkbook hssfWorkbook = null;
        hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);

        List<Area> list = new ArrayList<>();

        for (Row row : sheet) {

            if (row.getRowNum() == 0) {
                continue;
            }

            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();

            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);

            // 生成简码
            String[] headByString =
                    PinYin4jUtils.getHeadByString(province + city + district);

            String shortcode =
                    PinYin4jUtils.stringArrayToString(headByString, "");
            // 生成城市编码
            String citycode = PinYin4jUtils.hanziToPinyin(city, "");

            Area area = new Area();

            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            area.setPostcode(postcode);
            area.setCitycode(citycode);
            area.setShortcode(shortcode);

            list.add(area);
        }

        hssfWorkbook.close();

        areaService.save(list);

        return SUCCESS;
    }

}
