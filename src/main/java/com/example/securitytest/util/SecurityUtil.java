package com.example.securitytest.util;

import com.example.securitytest.pojo.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author : whz
 * @date : 2023/5/17 18:33
 */
public class SecurityUtil {

    private SecurityUtil() {}

    public static SysUser getLoginUser(){
        return (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
