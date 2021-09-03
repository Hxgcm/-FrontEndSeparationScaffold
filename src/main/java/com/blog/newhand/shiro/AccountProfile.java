package com.blog.newhand.shiro;

import java.io.Serializable;

/**
 * @author HeXianGang
 * @description 用户类
 * @create 2021-09-03 21:35
 */
public class AccountProfile implements Serializable {

    private Long id;
    private String username;
    private String avatar;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
