package com.blog.newhand.entity.dto.LoginDTO;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author HeXianGang
 * @description
 * @create 2021-09-05 14:17
 */
@Component
public class LoginDTO implements Serializable {


    @NotBlank(message = "用户名不能为空！")
    private String username;

    @NotBlank(message = "密码不能为空!")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String
    toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
