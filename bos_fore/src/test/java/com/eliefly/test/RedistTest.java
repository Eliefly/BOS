package com.eliefly.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ClassName:RedistTest <br/>
 * Function: <br/>
 * Date: 2017年12月6日 下午3:30:42 <br/>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedistTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void test01() {

        redisTemplate.opsForValue().set("name", "jack");

        // 设置数据的有效时间, 10 秒
        redisTemplate.opsForValue().set("age", "23333", 10, TimeUnit.SECONDS);

        redisTemplate.delete("name");

    }

}
