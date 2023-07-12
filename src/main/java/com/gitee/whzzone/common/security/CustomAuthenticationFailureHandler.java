package com.gitee.whzzone.common.security;

import cn.hutool.json.JSONUtil;
import com.gitee.whzzone.web.Result;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : whz
 * @date : 2023/5/17 11:51
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 构造一个统一返回格式对象
        Result result = new Result();
        result.setCode(Result.ERROR);
        result.setMsg("认证失败");

        // 以 JSON 格式写入 response
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONUtil.toJsonStr(result));
        writer.flush();
    }
}
