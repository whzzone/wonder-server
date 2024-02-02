package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.system.pojo.auth.UsernameLoginDTO;
import com.gitee.whzzone.admin.system.pojo.auth.WxLoginDTO;
import com.gitee.whzzone.web.pojo.other.Result;

import java.util.Map;

/**
 * @author : whz
 * @date : 2023/5/17 16:29
 */
public interface AuthService {
    Map<String, String> getCode();
    
    Result<?> loginByUsername(UsernameLoginDTO dto);

    void logout();

    Result loginByWeixin(WxLoginDTO dto);
}
