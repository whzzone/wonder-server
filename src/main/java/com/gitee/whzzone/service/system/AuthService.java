package com.gitee.whzzone.service.system;

import com.gitee.whzzone.common.security.LoginUser;
import com.gitee.whzzone.pojo.dto.auth.EmailLoginDto;
import com.gitee.whzzone.pojo.dto.auth.UsernameLoginDto;
import com.gitee.whzzone.pojo.dto.auth.WxLoginDto;
import com.gitee.whzzone.web.Result;

import java.util.Map;

/**
 * @author : whz
 * @date : 2023/5/17 16:29
 */
public interface AuthService {
    Map<String, String> getCode();
    
    Result<LoginUser> loginByUsername(UsernameLoginDto dto);
    
    Result loginByEmail(EmailLoginDto dto);

    Result sendEmail(String email);

    void logout();

    Result loginByWeixin(WxLoginDto dto);
}
