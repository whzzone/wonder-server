package com.example.securitytest.common.security;

import com.example.securitytest.pojo.entity.User;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/7
 */
@Data
public class LoginUser extends User {

    private String token;
}
