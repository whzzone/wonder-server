package com.gitee.whzzone.util;

import com.gitee.whzzone.common.security.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author : whz
 * @date : 2023/5/17 18:33
 */
public class SecurityUtil {

    private SecurityUtil() {}

    /**
     * 超级管理员角色编码
     */
    public static final String SUPER_ADMIN = "super_admin";

    public static LoginUser getLoginUser(){
        return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
