package com.yl.controller;

import com.yl.pojo.User;
import com.yl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*基于用户名查询用户信息, 后续在sso-auth服务中会对这个方法进行远程调用*/

    @GetMapping("/login/{username}")
    public User doSelectUserByUsername(@PathVariable("username") String username) {
        return userService.selectUserByUsername(username);
    }

    /*基于用户ID查询用户权限, 后续会在sso-auth服务中会对这个方法进行远程调用*/
    @GetMapping("/permission/{userId}")
    public List<String> doSelectUserPermissions(@PathVariable("userId") Long userId) {
        return userService.selectUserPermissions(userId);
    }

}