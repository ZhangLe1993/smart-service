package com.biubiu.user.controller;

import com.biubiu.thrift.user.UserInfo;
import com.biubiu.user.core.ApiResult;
import com.biubiu.user.core.Config;
import com.biubiu.user.thrift.ServiceProvider;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.MessageDigest;
import java.util.Random;

/**
 * @author yule.zhang
 * @date 2019/5/9 21:09
 * @email zhangyule1993@sina.com
 * @description 用户服务
 */

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private ServiceProvider serviceProvider;

    @RequestMapping("/login")
    public ApiResult login(@RequestParam("username") String username, @RequestParam("password") String password) {

        //1、验证用户名密码
        UserInfo userInfo = null;
        try {
            serviceProvider.getUserService().getUserByName(username);
        }catch(TException e){
            e.printStackTrace();
            return Config.USERNAME_PASSWORD_INVALID;
        }
        if(userInfo == null) {
            return Config.USERNAME_PASSWORD_INVALID;
        }

        if(userInfo.getPassword().equals(md5(password))) {
            return Config.USERNAME_PASSWORD_INVALID;
        }
        //2、生成token

        String token = genToken();
        // 3、 缓存用户

        return null;
    }

    private String md5(String password) {
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(password.getBytes());
            return HexUtils.toHexString(md5Bytes);

        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String genToken() {

        return randomCode("0123456789abcdefghijkmlnopqrstuvwxyz", 32);
    }

    private String randomCode(String str, int size) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < size; i++) {
            int loc = random.nextInt(str.length());
            result.append(str.charAt(loc));
        }

        return result.toString();
    }
}
