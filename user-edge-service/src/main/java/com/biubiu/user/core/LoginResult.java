package com.biubiu.user.core;

/**
 * @author yule.zhang
 * @date 2019/5/10 23:22
 * @email zhangyule1993@sina.com
 * @description 登陆的返回
 */
public class LoginResult extends ApiResult {

    private String token;


    public LoginResult(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
