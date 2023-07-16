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
     * 优先级最高的角色id
     */
    public static Long getPriorRoleId() {
        return 1658729760682242048L;
    }
}
