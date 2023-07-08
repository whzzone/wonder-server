package com.example.securitytest.service;

import com.example.securitytest.common.weixin.WxLoginDto;
import com.example.securitytest.pojo.dto.EmailLoginDto;
import com.example.securitytest.pojo.dto.auth.UsernameLoginDto;
import com.gitee.whzzone.web.Result;

import java.util.Map;

/**
 * @author : whz
 * @date : 2023/5/17 16:29
 */
public interface AuthService {
    Map<String, String> getCode();
    
    Result loginByUsername(UsernameLoginDto dto);
    
    Result loginByEmail(EmailLoginDto dto);

    Result sendEmail(String email);

    void logout();

    Result loginByWeixin(WxLoginDto dto);
}
