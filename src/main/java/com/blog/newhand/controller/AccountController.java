package com.blog.newhand.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.newhand.common.lang.Result;
import com.blog.newhand.entity.User;
import com.blog.newhand.entity.dto.LoginDTO.LoginDTO;
import com.blog.newhand.service.UserService;
import com.blog.newhand.utils.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author HeXianGang
 * @description 用户登录的类
 * @create 2021-09-05 14:12
 */
@RestController
public class AccountController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    /**
     * 用户登录的接口
     *
     * @param loginDTO
     * @param response
     * @return
     */
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDTO.getUsername()));
        Assert.notNull(user, "用户不存在！");

        if (!user.getPassword().equals(SecureUtil.md5(loginDTO.getPassword()))) {
            return Result.fail("密码错误！");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        return Result.success(MapUtil.builder()
                                     .put("id", user.getId())
                                     .put("username", user.getUsername())
                                     .put("avatar", user.getAvatar())
                                     .put("email", user.getEmail())
                                     .map());

    }

    /**
     * 退出登录方法
     *
     * 主要使用Security工具类的logout
     *
     * @return
     */
    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout(){
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }
}
