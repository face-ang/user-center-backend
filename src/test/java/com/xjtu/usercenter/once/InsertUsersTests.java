package com.xjtu.usercenter.once;

import com.xjtu.usercenter.mapper.UserMapper;
import com.xjtu.usercenter.model.domain.User;
import com.xjtu.usercenter.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//@SpringBootTest
public class InsertUsersTests {
    @Resource
    private UserService userService;

    /**
     * 批量插入用户
     */
//    @Test
    public void doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("假用户");
            user.setUserAccount("fakewy");
            user.setAvatarUrl("https://img0.baidu.com/it/u=744547528,3161216612&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501");
            user.setGender(0);
            user.setUserPassword("123456789");
            user.setPhone("4568559624");
            user.setEmail("123@qq.com");
            user.setUserStatus(0);
            user.setPlanetCode("111111");
            user.setTags("[]");
            user.setUserRole(0);
            users.add(user);
        }
        userService.saveBatch(users, 100);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    /**
     * 并发插入用户
     */
//    @Test
    public void doConcurrencyInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 分十组
        int j = 0;
        ArrayList<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<User> userList = new ArrayList<>();
            while(true) {
                j++;
                User user = new User();
                user.setUsername("假用户");
                user.setUserAccount("fakewy");
                user.setAvatarUrl("https://img0.baidu.com/it/u=744547528,3161216612&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=501");
                user.setGender(0);
                user.setUserPassword("123456789");
                user.setPhone("4568559624");
                user.setEmail("123@qq.com");
                user.setUserStatus(0);
                user.setPlanetCode("111111");
                user.setTags("[]");
                user.setUserRole(0);
                userList.add(user);
                if (j % 10000 == 0) {
                    break;
                }
            }
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                System.out.println("threadName:" + Thread.currentThread().getName());
                userService.saveBatch(userList, 10000);
            });
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
