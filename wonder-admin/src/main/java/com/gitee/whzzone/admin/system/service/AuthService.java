package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.system.pojo.auth.UsernameLoginDto;
import com.gitee.whzzone.admin.system.pojo.auth.WxLoginDto;
import com.gitee.whzzone.web.pojo.other.Result;

import java.util.Map;

/**
 * @author : whz
 * @date : 2023/5/17 16:29
 */
public interface AuthService {
    Map<String, String> getCode();
    
    Result<LoginUser> loginByUsername(UsernameLoginDto dto);

    void logout();

    Result loginByWeixin(WxLoginDto dto);
}
