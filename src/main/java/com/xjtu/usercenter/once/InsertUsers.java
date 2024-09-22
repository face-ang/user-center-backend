package com.xjtu.usercenter.once;

import com.xjtu.usercenter.mapper.UserMapper;
import com.xjtu.usercenter.model.domain.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

@Component
public class InsertUsers {
    @Resource
    private UserMapper userMapper;

    /**
     * 批量插入用户
     */
//    @Scheduled(initialDelay = 5000, fixedRate = Long.MAX_VALUE)
    public void doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;
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
//            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
