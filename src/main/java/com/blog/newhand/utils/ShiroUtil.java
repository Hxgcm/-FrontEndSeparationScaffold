package com.blog.newhand.utils;

import com.blog.newhand.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

import java.security.Security;

/**
 * @author HeXianGang
 * @description ShiroUtil
 * @create 2021-09-05 15:10
 */
public class ShiroUtil {

    /**
     * 获取当前登录用户的信息
     * @return
     */
    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
