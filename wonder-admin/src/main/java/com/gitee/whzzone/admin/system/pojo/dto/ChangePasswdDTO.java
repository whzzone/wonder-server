package com.gitee.whzzone.admin.system.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Create by whz at 2024/2/21
 */
@Data
public class ChangePasswdDTO {

    @ApiModelProperty(value = "旧密码", required = true)
    @Size(min = 6, max = 16, message ="密码必须6-16之间")
    private String oldPasswd;

    @ApiModelProperty(value = "新密码", required = true)
    @Size(min = 6, max = 16, message ="密码必须6-16之间")
    @Pattern(regexp = "^[A-Za-z0-9]{6,16}$", message ="密码必须为6-16位的数字或英文字母")
    private String newPasswd;

}
