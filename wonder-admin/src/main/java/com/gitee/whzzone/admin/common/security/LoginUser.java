package com.gitee.whzzone.admin.common.security;

import com.gitee.whzzone.admin.system.entity.User;
import com.gitee.whzzone.admin.system.pojo.dto.DeptDto;
import com.gitee.whzzone.admin.system.pojo.dto.RoleDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Create by whz at 2023/7/7
 */
@Data
public class LoginUser extends User {

    @ApiModelProperty("token")
    private String token;

    @ApiModelProperty("token过期时间")
    private Long expire;

    @ApiModelProperty("当前请求选择的角色ID")
    private Integer currentRoleId;

    @ApiModelProperty("当前请求选择的部门ID")
    private Integer currentDeptId;

    @ApiModelProperty("用户的所有角色ID")
    private List<Integer> roleIds;

    @ApiModelProperty("用户的所有部门ID")
    private List<Integer> deptIds;

    @ApiModelProperty("部门List")
    private List<DeptDto> deptList;

    @ApiModelProperty("角色List")
    private List<RoleDto> roleList;
}
