package com.blog.newhand.shiro;

import cn.hutool.json.JSONUtil;
import com.blog.newhand.common.lang.Result;
import com.blog.newhand.utils.JwtUtils;

import io.jsonwebtoken.Claims;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author HeXianGang
 * @description JwtFilter
 * @create 2021-09-02 22:51
 */
@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");

        if (StringUtils.isEmpty(jwt)) {
            return null;
        }
        return new JwtToken(jwt);
    }

    /**
     * 因为Shiro框架中，对用于token会进行校验
     * 如果出现用户不存在什么的，就会抛出异常向客户端发送数据
     * 但是本系统是前后端分离的，需要给前端返回固定格式的Json数据
     * 所以重写 AuthenticatingFilter onLoginFailure()
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Throwable throwable = e.getCause() == null ? e : e.getCause();

        Result fail = Result.fail(throwable.getMessage());

        String jsonStr = JSONUtil.toJsonStr(fail);


        try {
            httpServletResponse.getWriter().print(jsonStr);
        } catch (IOException ioException) {

        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        // 获取Jwt
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        // 校验：如果用户访问没有jwt,这直接去访问接口，由@Rose 进行控制权限
        if (StringUtils.isEmpty(jwt)) {
            return true;
        } else {
            // 如果有jwt,这需要对jwt进行校验，然后进行登录操作
            Claims claimByToken = jwtUtils.getClaimByToken(jwt);
            // 判断用户的token是否正确以及是否过期
            if (claimByToken == null || jwtUtils.isTokenExpired(claimByToken.getExpiration())) {
                throw new ExpiredCredentialsException("token已失效，请重新登录！");
            }

            // 如果用户的token正确且不过期，执行登录
            return executeLogin(servletRequest, servletResponse);

        }
    }
}
