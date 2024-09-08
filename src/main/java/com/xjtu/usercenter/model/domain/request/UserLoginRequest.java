package com.xjtu.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户denglu请求体
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 7694845848492487661L;
    private String userAccount;
    private String userPassword;
}
