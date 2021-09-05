package com.blog.newhand.common.exception;

import com.blog.newhand.common.lang.Result;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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


    /**
     * 实体校验异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {
        System.out.println("MethodArgumentNotValidException " + e.toString());
        // 对实体校验异常进行过滤，只保留第一个异常
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.fail(objectError.getDefaultMessage());
    }

    /**
     * 用户登录异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) {
        System.out.println("IllegalArgumentException,登录异常： " + e.toString());
        return Result.fail(e.getMessage());
    }
}
