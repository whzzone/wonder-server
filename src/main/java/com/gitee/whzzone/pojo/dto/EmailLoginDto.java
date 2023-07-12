package com.gitee.whzzone.pojo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author : whz
 * @date : 2023/5/17 16:23
 */
@Data
public class EmailLoginDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String code;
}
