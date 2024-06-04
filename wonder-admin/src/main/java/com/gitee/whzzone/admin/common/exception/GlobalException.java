package com.gitee.whzzone.admin.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
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
        e.printStackTrace();
//        log.error(e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    public Result<?> notLoginExceptionHandler(NotLoginException e) {
        return Result.error(Result.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(NotPermissionException.class)
    @ResponseBody
    public Result<?> notPermissionExceptionHandler(NotPermissionException e) {
        return Result.error(Result.NO_PERMISSION, e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result<?> nullPointerExceptionHandler(NullPointerException e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        String msg = "空指针异常：" + stackTrace[0].getFileName() + " line：" + stackTrace[0].getLineNumber();
        log.error(msg);
        return Result.error(msg);
    }

    @ExceptionHandler(FrequencyLimitException.class)
    @ResponseBody
    public Result<?> frequencyLimitExceptionHandler(FrequencyLimitException e) {
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public Result<?> userNotFoundExceptionHandler(UserNotFoundException e) {
        return Result.error(Result.USER_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(LimitLoginException.class)
    @ResponseBody
    public Result<?> limitLoginExceptionHandler(LimitLoginException e) {
        return Result.error(Result.LIMIT_LOGIN, e.getMessage());
    }

}
