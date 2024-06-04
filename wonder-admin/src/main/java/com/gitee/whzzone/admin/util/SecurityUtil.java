package com.gitee.whzzone.admin.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : whz
 * @date : 2023/5/17 18:33
 */
@Slf4j
@Component
public class SecurityUtil {

    private static final TransmittableThreadLocal<LoginUser> LOGIN_USER_CONTEXT = new TransmittableThreadLocal<>();

    private static UserService userService;

    private SecurityUtil() {}

    @Autowired
    public void setUserService(UserService userService) {
        SecurityUtil.userService = userService;
    }

    /**
     * 超级管理员角色编码 和 账号
     */
    public static final String ADMIN_ROLE_CODE = "admin";
    public static final Integer ADMIN_USER_ID = 1;

    /**
     * 获取登录用户上下文
     */
    public static LoginUser getLoginUser() {
        return LOGIN_USER_CONTEXT.get();
    }

    /**
     * 设置登录用户上下文
     * @param loginUser 登录用户
     */
    public static void setLoginUser(LoginUser loginUser) {
        LOGIN_USER_CONTEXT.set(loginUser);
        log.debug("---------------------设置登录用户上下文");
    }

    /**
     * 清除登录用户上下文
     */
    public static void clearLoginUser() {
        LOGIN_USER_CONTEXT.remove();
        log.debug("---------------------清除登录用户上下文");
    }

    /**
     * 判断是否管理员
     */
    public static boolean isAdmin(){
        return getLoginUser().getId().equals(ADMIN_USER_ID);
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

    /**
     * 获取当前请求的用户id
     */
    public static Integer getCurrentUserId() {
        return getLoginUser().getId();
    }

    /**
     * 超级管理员内部登录(无权限限制)
     */
    public static void innerLogin() {
        innerLogin(1);
    }

    /**
     * 指定用户内部登录
     * @param userId 登录的用户id
     */
    public static void innerLogin(Integer userId) {
        LoginUser loginUser = userService.getLoginUserInfo(userId);
        setLoginUser(loginUser);
    }
}
