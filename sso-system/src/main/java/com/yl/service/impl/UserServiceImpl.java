package com.yl.service.impl;

import com.yl.dao.UserMapper;
import com.yl.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yl.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    public List<String> selectUserPermissions(Long userId) {
        // 方案一: 在这里可以调用数据层的单表查询方法, 查询三次获取用户信息
        // 方案二: 在这里可以调用数据层的多表嵌套或多表关联方式执行1次查询

        return userMapper.selectUserPermissions(userId);
    }

}
