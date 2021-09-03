package com.blog.newhand.common.exception;

import com.blog.newhand.common.lang.Result;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author HeXianGang
 * @description 全局异常处理
 * @create 2021-09-03 21:43
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 运行时异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        System.out.println("RuntimeException " + e.toString());
        return Result.fail(e.getMessage());
    }

    /**
     * 权限控制异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e) {
        System.out.println("ShiroException " + e.toString());
        return Result.fail(401, e.getMessage(), null);
    }
}
