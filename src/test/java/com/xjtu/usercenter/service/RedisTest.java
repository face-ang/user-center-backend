package com.xjtu.usercenter.service;

import com.xjtu.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        ValueOperations valueOperations = redisTemplate.opsForValue();  // 集合
        // 增
        valueOperations.set("wyString", "dal");
        valueOperations.set("wyInt", 1);
        valueOperations.set("wyDouble", 2.0);
        User user = new User();
        user.setId(1L);
        user.setUsername("wywy");
        valueOperations.set("wyUser", user);
        // 查
        Object wyString = valueOperations.get("wyString");
        Object wyInt = valueOperations.get("wyInt");
        Object wyDouble = valueOperations.get("wyDouble");
        Object wyUser = valueOperations.get("wyUser");

        System.out.println(wyString);
        System.out.println(wyDouble);
        System.out.println(wyInt);
        System.out.println(wyUser);
        // 修改
        valueOperations.set("wyString", "dal66666");
        // 删除
        redisTemplate.delete("wyString");
    }
}
