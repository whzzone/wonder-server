package com.gitee.whzzone.admin.common.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gitee.whzzone.admin.common.serializer.ListLongSerializer;
import com.gitee.whzzone.admin.common.serializer.LongSerializer;
import com.gitee.whzzone.admin.pojo.dto.system.DeptDto;
import com.gitee.whzzone.admin.pojo.dto.system.RoleDto;
import com.gitee.whzzone.admin.pojo.entity.system.User;
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

    @JsonSerialize(using = LongSerializer.class)
    @ApiModelProperty("当前请求选择的角色ID")
    private Long currentRoleId;

    @JsonSerialize(using = LongSerializer.class)
    @ApiModelProperty("当前请求选择的部门ID")
    private Long currentDeptId;

    @JsonSerialize(using = ListLongSerializer.class)
    @ApiModelProperty("用户的所有角色ID")
    private List<Long> roleIds;

    @JsonSerialize(using = ListLongSerializer.class)
    @ApiModelProperty("用户的所有部门ID")
    private List<Long> deptIds;

    @ApiModelProperty("部门List")
    private List<DeptDto> deptList;

    @ApiModelProperty("角色List")
    private List<RoleDto> roleList;
}