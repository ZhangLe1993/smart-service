package com.biubiu.user.core;

/**
 * @author yule.zhang
 * @date 2019/5/9 21:57
 * @email zhangyule1993@sina.com
 * @description 静态资源
 */
public class Config {

    public static final ApiResult USERNAME_PASSWORD_INVALID = new ApiResult(1001, "username or password invalid");

    public static final ApiResult MOBILE_OR_EMAIL_INVALID = new ApiResult(1002, "mobile or email invalid");

    public static final ApiResult SEND_VERIFYCODE_FAILED = new ApiResult(1003, "send verify code failed");

    public static final ApiResult VERIFY_CODE_INVALID = new ApiResult(1004, "verifyCode invalid");


    public static final ApiResult SUCCESS = new ApiResult();
}
