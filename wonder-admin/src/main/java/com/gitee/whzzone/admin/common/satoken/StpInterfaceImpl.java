package com.gitee.whzzone.admin.common.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.gitee.whzzone.admin.system.pojo.dto.RoleDTO;
import com.gitee.whzzone.admin.util.SecurityUtil;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限认证接口扩展
 * @author Create by whz at 2024/2/1
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        if (SecurityUtil.isAdmin()) {
            return Collections.singletonList("*");
        }
        return SecurityUtil.getLoginUser().getPermissions();
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        if (SecurityUtil.isAdmin()) {
            return Collections.singletonList("*");
        }
        return SecurityUtil.getLoginUser().getRoles().stream().map(RoleDTO::getCode).collect(Collectors.toList());
    }
}
