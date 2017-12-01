package com.elifly.test;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eliefly.bos.dao.base.StandardRepository;
import com.eliefly.bos.domain.base.Standard;

/**
 * ClassName:SpringDataJPATest <br/>
 * Function: <br/>
 * Date: Nov 27, 2017 8:25:37 PM <br/>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringDataJPATest {

    @Autowired
    private StandardRepository standardDao;

    // 增加
    @Test
    public void test_save() {

        Standard standard = new Standard();
        standard.setName("张飞");
        standard.setMaxLength(100);
        standard.setMaxWeight(20);
        standard.setMinLength(5);
        standard.setMinWeight(1);
        standard.setOperatingCompany("腾讯科技");
        standard.setOperatingTime(new Date());

        standardDao.save(standard);
    }

    // 修改
    @Test
    public void test_update() {
        Standard standard = new Standard();
        standard.setId(1L);
        standard.setOperatingCompany("阿里巴巴");

        standardDao.save(standard);

    }

    // 查找
    @Test
    public void test_findOne() {

        Standard findOne = standardDao.findOne(1L);
        System.out.println(findOne);
    }

    // 删除
    @Test
    public void test_delete() {

        standardDao.delete(1L);
    }

    // 依据姓名查询
    @Test
    public void test_findByName() {

        Standard standard = standardDao.findByName("张三");
        System.out.println(standard);
    }

    // 依据姓名模糊查询
    @Test
    public void test_findByNameLike() {

        // Standard standard = standardDao.findByNameLike("%张%");

        List<Standard> list = standardDao.findByNameLike("%张%");
        System.out.println(list);
    }

    // 查询操作员为空的
    @Test
    public void test_findByOperatorNull() {

        Standard standard = standardDao.findByOperatorNull();
        System.out.println(standard);
    }

    // 多条件查询
    @Test
    public void test_findByNameAndOperator() {

        Standard standard = standardDao.findByNameAndOperator("张三", "空运");
        System.out.println(standard);
    }

    // JPQL 查询
    @Test
    public void test_findByNameAndOperatorJPQL() {

        Standard standard = standardDao.findByNameAndOperatorJPQL("空运", "张三");
        System.out.println(standard);
    }

    // SQL 查询
    @Test
    public void test_findByNameAndOperatorSQL() {

        Standard standard = standardDao.findByNameAndOperatorSQL("张三", "空运");
        System.out.println(standard);
    }

    // 查询所有
    @Test
    public void test_findAll() {

        List<Standard> list = standardDao.findAll();
        System.out.println(list);
    }

}
