package com.gitee.whzzone.admin.system.pojo.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author : whz
 * @date : 2023/5/17 16:23
 */
@Data
public class LoginDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String code;

    @NotBlank
    private String uuid;
}
