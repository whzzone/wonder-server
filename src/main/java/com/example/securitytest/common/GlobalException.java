package com.example.securitytest.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常
 */
@Slf4j
@ControllerAdvice
public class GlobalException {
    /**
     * 所有的【参数校验失败】情况会经过这里处理
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<List<String>> validateHandler(MethodArgumentNotValidException ex) {
        ex.printStackTrace();

        List<String> list = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMsg = error.getDefaultMessage();
            list.add(errorMsg);
        });

        return Result.error(list.toString());
    }

/*	@ResponseBody
	@ExceptionHandler(AccessDeniedException.class)
	public Result handleAccessRE(AccessDeniedException ex){
		return Result.error(GlobalConstant.COMMON_ERROR,"当前用户没有权限，请联系管理员");
	}*/

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result defaultExceptionHandler(HttpServletRequest req, HttpMessageNotReadableException e) {
        e.printStackTrace();
        return Result.error("参数转换异常，请检查参数是否正确");
    }

    /**
     * 非【参数校验失败】情况会经过这里除了
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result defaultExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return Result.error(e.getMessage() == null ? "系统错误，请联系管理员！" : e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result accessDeniedException(AccessDeniedException e) {
        return Result.error("未拥有访问权限：" + e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public Result<String> badCredentialsException(BadCredentialsException e) {
        return Result.error("认证失败1：" + e.getMessage());
    }
}
