package com.example.securitytest.common;

import com.gitee.whzzone.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常
 */
@Slf4j
@ControllerAdvice
public class GlobalException {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<List<String>> validateHandler(MethodArgumentNotValidException e) {

        List<String> list = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMsg = error.getDefaultMessage();
            list.add(errorMsg);
        });

        log.error(list.toString());

        return Result.error(list.toString());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result defaultExceptionHandler(Exception e) {
//        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result accessDeniedException(AccessDeniedException e) {
        return Result.error("未拥有访问权限：" + e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public Result<String> badCredentialsException(BadCredentialsException e) {
        return Result.error("认证失败：" + e.getMessage());
    }
}
