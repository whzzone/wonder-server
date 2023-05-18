package com.example.securitytest.pojo.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : whz
 * @date : 2023/5/18 10:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessDto {

    private String token;

    private Long expire;

}
