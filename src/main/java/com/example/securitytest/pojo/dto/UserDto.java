package com.example.securitytest.pojo.dto;

import com.example.securitytest.common.LongSerializer;
import com.example.securitytest.common.validation.group.CreateGroup;
import com.example.securitytest.common.validation.group.UpdateGroup;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDto extends BaseDto<UserDto> {

    @NotNull(message = "id不能为空",groups = UpdateGroup.class)
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @NotBlank(message = "username不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    private String username;

    @NotBlank(message = "phone不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    private String phone;

    @NotBlank(message = "nickname不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    private String nickname;

    private String email;

    private String password;

    private String openId;

    private String unionId;

    private Boolean enabled;

    @ApiModelProperty("部门名称")
    private String deptName;

    @NotNull(message = "部门id不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @JsonSerialize(using = LongSerializer.class)
    @ApiModelProperty("部门id")
    private Long deptId;

}
