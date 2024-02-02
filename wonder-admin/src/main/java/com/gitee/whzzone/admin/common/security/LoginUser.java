package com.gitee.whzzone.admin.common.security;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.gitee.whzzone.admin.system.entity.User;
import com.gitee.whzzone.admin.system.pojo.dto.DeptDTO;
import com.gitee.whzzone.admin.system.pojo.dto.RoleDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Create by whz at 2023/7/7
 */
@Data
public class LoginUser extends User {

    @ApiModelProperty("令牌信息")
    private SaTokenInfo tokenInfo;

    @ApiModelProperty("当前请求选择的角色ID")
    private Integer currentRoleId;

    @ApiModelProperty("当前请求选择的部门ID")
    private Integer currentDeptId;

    @ApiModelProperty("部门List")
    private List<DeptDTO> depts;

    @ApiModelProperty("角色List")
    private List<RoleDTO> roles;

    @ApiModelProperty("权限")
    private List<String> permissions;

    public SaTokenInfo getTokenInfo() {
        return StpUtil.getTokenInfo();
    }
}
