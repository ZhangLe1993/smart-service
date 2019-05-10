package com.biubiu.user.service;

import com.biubiu.thrift.user.UserInfo;
import com.biubiu.thrift.user.UserService;
import com.biubiu.user.mapper.UserMapper;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yule.zhang
 * @date 2019/5/9 0:32
 * @email zhangyule1993@sina.com
 * @description 实现用户服务
 */

@Service
public class UserServiceImpl implements UserService.Iface {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUserById(int id) throws TException {
        return userMapper.getUserById(id);
    }

    @Override
    public UserInfo getUserByName(String username) throws TException {
        return userMapper.getUserByName(username);
    }

    @Override
    public void registerUser(UserInfo userInfo) throws TException {

        userMapper.registerUser(userInfo);
    }
}
