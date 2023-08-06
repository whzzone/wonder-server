package com.gitee.whzzone.admin.pojo.dto.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author : whz
 * @date : 2023/1/11 14:32
 */
@Data
public class ResetPWDDto {

    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空")
    private Long id;

    @ApiModelProperty(value = "新密码", required = true)
    @Size(min = 6, max = 16, message ="密码必须6-16之间")
    @Pattern(regexp = "^[A-Za-z0-9]{6,16}$", message ="密码必须为6-16位的数字或英文字母")
    private String password;

}
