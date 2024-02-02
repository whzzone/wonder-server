package com.gitee.whzzone.admin.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.SaTokenException;
import com.gitee.whzzone.web.pojo.other.Result;
import lombok.extern.slf4j.Slf4j;
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
    public Result<?> validateHandler(MethodArgumentNotValidException e) {

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
    public Result<?> defaultExceptionHandler(Exception e) {
//        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    @ExceptionHandler({NotLoginException.class, NotPermissionException.class})
    @ResponseBody
    public Result<?> loginExceptionHandler(SaTokenException e) {
        return Result.error(Result.NO_PERMISSION, e.getMessage());
    }

}
