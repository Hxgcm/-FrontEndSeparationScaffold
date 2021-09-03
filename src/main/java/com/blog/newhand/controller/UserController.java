package com.blog.newhand.controller;


import com.blog.newhand.common.lang.Result;
import com.blog.newhand.entity.User;
import com.blog.newhand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-09-01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public Object index() {
        User user = userService.getById(1L);
        return Result.success(200, "操作成功", user);
    }
}
