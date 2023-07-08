package com.example.securitytest.pojo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author : whz
 * @date : 2023/1/11 14:32
 */
@Data
public class ResetPWDDto {
    @NotNull(message = "用户id不能为空")
    private Integer id;

    @Length(min = 6, max = 16, message ="密码必须6-16之间")
    private String password;
}
