package com.gitee.whzzone.admin.system.pojo.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author : whz
 * @date : 2023/5/19 16:34
 */
@Data
public class WxLoginDTO {

    @NotBlank(message = "login code 不能为空")
    private String code;

}
