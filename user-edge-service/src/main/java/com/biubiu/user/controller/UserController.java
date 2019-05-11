package com.biubiu.user.controller;

import com.biubiu.thrift.user.UserInfo;
import com.biubiu.user.cache.RedisClient;
import com.biubiu.user.core.ApiResult;
import com.biubiu.user.core.Config;
import com.biubiu.user.core.LoginResult;
import com.biubiu.user.dto.UserDTO;
import com.biubiu.user.thrift.ServiceProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private RedisClient redisClient;

    @RequestMapping("/login")
    @ResponseBody
    public ApiResult login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {

        //1、验证用户名密码
        UserInfo userInfo = null;
        try {
            userInfo = serviceProvider.getUserService().getUserByName(username);
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

        redisClient.set(token, toDTO(userInfo), 3600);


        return new LoginResult(token);
    }


    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult sendCode(@RequestParam(value= "mobile",required = false) String mobile,
                              @RequestParam(value= "email",required = false) String email) {

        String message = "Verify code is:";
        String code = randomCode("0123456789", 6);
        try {
            boolean result = false;
            if(StringUtils.isNotBlank(mobile)) {
                result = serviceProvider.getMessageService().sendMobileMessage(mobile, message + code);
                redisClient.set(mobile, code);
            } else if(StringUtils.isNotBlank(email)) {
                result = serviceProvider.getMessageService().sendEmailMessage(email, message + code);
                redisClient.set(email, code);
            } else {
                return Config.MOBILE_OR_EMAIL_INVALID;
            }
            if(!result) {
                return Config.MOBILE_OR_EMAIL_INVALID;
            }
        } catch (TException e) {
            e.printStackTrace();
            return ApiResult.exception(e);
        }
        return Config.SUCCESS;
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult register(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam(value= "mobile",required = false) String mobile,
                              @RequestParam(value= "email",required = false) String email,
                              @RequestParam("validate_code") String validateCode) {

        if(StringUtils.isBlank(mobile) && StringUtils.isBlank(email)) {
            return Config.MOBILE_OR_EMAIL_INVALID;
        }
        if(StringUtils.isNotBlank(mobile)) {
            String redisCode = redisClient.get(mobile);
            if(!validateCode.equals(redisCode)) {
                return Config.VERIFY_CODE_INVALID;
            }
        }else {
            String redisCode = redisClient.get(email);
            if(!validateCode.equals(redisCode)) {
                return Config.VERIFY_CODE_INVALID;
            }
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(md5(password));
        userInfo.setMobile(mobile);
        userInfo.setEmail(email);
        try {
            serviceProvider.getUserService().registerUser(userInfo);
        } catch (TException e) {
            e.printStackTrace();
            return ApiResult.exception(e);
        }
        return Config.SUCCESS;
    }


    @RequestMapping(value = "/auth",method = RequestMethod.POST)
    @ResponseBody
    public UserDTO auth(@RequestHeader("token") String token) {
        return redisClient.get(token);
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


    private Object toDTO(UserInfo userInfo) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userInfo, userDTO);
        return userDTO;
    }
}
