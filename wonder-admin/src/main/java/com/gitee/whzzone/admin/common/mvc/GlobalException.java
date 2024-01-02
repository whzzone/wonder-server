package com.gitee.whzzone.admin.common.mvc;

import com.gitee.whzzone.common.exception.NoDataException;
import com.gitee.whzzone.web.pojo.other.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
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
    public Result accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        return Result.error("无权限访问接口：" + path);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public Result<String> badCredentialsException(BadCredentialsException e) {
        return Result.error("认证失败：" + e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Result handleNotFoundException(HttpServletRequest request, NoHandlerFoundException ex) {
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        return Result.error("访问不存在的接口：" + path);
    }

    @ExceptionHandler(NoDataException.class)
    @ResponseBody
    public Result handleNotFoundException() {
        return Result.error(Result.NO_DADA, "暂无数据");
    }
}
