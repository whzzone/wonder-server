package com.example.securitytest.util;

import com.example.securitytest.common.security.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author : whz
 * @date : 2023/5/17 18:33
 */
public class SecurityUtil {

    private SecurityUtil() {}

    public static LoginUser getLoginUser(){
        return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
