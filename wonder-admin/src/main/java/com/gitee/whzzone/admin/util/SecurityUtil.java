package com.gitee.whzzone.admin.util;

import com.gitee.whzzone.admin.common.security.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author : whz
 * @date : 2023/5/17 18:33
 */
public class SecurityUtil {

    private SecurityUtil() {}

    /**
     * 超级管理员角色编码 和 账号
     */
    public static final String ADMIN = "admin";

    public static LoginUser getLoginUser(){
        return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isAdmin(){
        return false;
    }
    
    /**
     * 获取当前请求的角色id
     */
    public static Integer getCurrentRoleId() {
        return getLoginUser().getCurrentRoleId();
    }

    /**
     * 获取当前请求的部门id
     */
    public static Integer getCurrentDeptId() {
        return getLoginUser().getCurrentDeptId();
    }
}
