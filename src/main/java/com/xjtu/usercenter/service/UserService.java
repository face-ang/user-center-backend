package com.xjtu.usercenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xjtu.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author admin
 * @description 用户服务接口
 * @createDate 2024-07-20 11:46:25
 */
public interface UserService extends IService<User> {


    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param planetCode 星球编号
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount        用户账户
     * @param userPassword       用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser 脱敏前的对象
     * @return 脱敏后的安全对象
     */
    public User getSafetyUser(User originUser);

    /**
     * 用户登出
     * @param request
     * @return
     */
    int userLogOut(HttpServletRequest request);
}
