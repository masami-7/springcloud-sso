package com.yl.service;

import com.yl.pojo.User;

import java.util.List;

public interface UserService {

    User selectUserByUsername(String username);

    List<String> selectUserPermissions(Long userId);

}
