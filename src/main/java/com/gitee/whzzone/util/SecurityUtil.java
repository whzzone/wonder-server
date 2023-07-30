package com.gitee.whzzone.util;

import com.gitee.whzzone.common.security.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

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

    public static List<Long> loginUserId(){
        Long id = getLoginUser().getId();
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        return ids;
    }

    /**
     * 获取当前请求的角色id
     */
    public static Long getCurrentRoleId() {
        return getLoginUser().getCurrentRoleId();
    }

    /**
     * 获取当前请求的部门id
     */
    public static Long getCurrentDeptId() {
        return getLoginUser().getCurrentDeptId();
    }
}
