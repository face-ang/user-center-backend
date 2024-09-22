package com.xjtu.usercenter.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjtu.usercenter.model.domain.User;
import com.xjtu.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存预热任务
 */
@Component
@Slf4j
public class PreCacheJob {
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RedissonClient redissonClient;
    // 重点用户，不要写死
    private List<Long> mainUser = Arrays.asList(1L);
    @Scheduled(cron = "0 59 23 * * *")
    public void doCacheRecommendUser() {
        RLock lock = redissonClient.getLock("yangban:precachejob:docache:lock");
        try {
            // tryLock（waitTime，释放锁时间，时间单位） waitTime一定为0，只抢一次，抢不到锁不等待直接返回false
            // 看门狗机制：把释放时间改成 -1
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                for (Long userId : mainUser) {
                    QueryWrapper queryWrapper = new QueryWrapper();
                    Page<User> userPage = userService.page(new Page<>(1, 20), queryWrapper);
                    String redisKey = String.format("yangban:user:recommend:%s",userId);
                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                    // 写缓存(这里用try-catch是因为 就算缓存写入失败，也需要将查数据库的数据返回)
                    try {
                        valueOperations.set(redisKey, userPage, 30000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.error("redis set key error", e);
                    }
                }
            }
        } catch (InterruptedException e) {
            log.error("lock error", e);
        } finally {
            // 只能释放自己的锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }


    }

}
