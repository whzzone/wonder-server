package com.gitee.whzzone.common.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gitee.whzzone.common.serializer.ListLongSerializer;
import com.gitee.whzzone.common.serializer.LongSerializer;
import com.gitee.whzzone.pojo.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Create by whz at 2023/7/7
 */
@Data
public class LoginUser extends User {

    @ApiModelProperty("用户token")
    private String token;

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
}
