package com.xjtu.usercenter.service;

import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RedissonTest {

    @Resource
    private RedissonClient redissonClient;
    @Test
    public void testRedisson() {
        // list,数据存在本地的 JVM 内存中
        List<String> list = new ArrayList<>();
        list.add("wwwyyy");
        System.out.println("list:" + list.get(0));
//        list.remove(0);

        // rlist,数据存在redis中
        RList<String> rlist = redissonClient.getList("test-list");
        rlist.add("wwyy");
        System.out.println("rlist:" + rlist.get(0));
//        rlist.remove(0);
    }
}
