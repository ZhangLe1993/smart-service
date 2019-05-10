package com.biubiu.user.core;

import java.io.Serializable;

/**
 * @author yule.zhang
 * @date 2019/5/9 21:32
 * @email zhangyule1993@sina.com
 * @description 返回提
 */
public class ApiResult implements Serializable {

    private int code;

    private String message;


    public ApiResult() {
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
