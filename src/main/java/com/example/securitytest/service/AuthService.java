package com.example.securitytest.service;

import com.example.securitytest.common.Result;
import com.example.securitytest.pojo.dto.LoginDto;

/**
 * @author : whz
 * @date : 2023/5/17 16:29
 */
public interface AuthService {
    Result login(LoginDto dto);
}
