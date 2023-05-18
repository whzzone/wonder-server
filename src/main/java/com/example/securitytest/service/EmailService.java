package com.example.securitytest.service;

/**
 * @author : whz
 * @date : 2023/5/18 8:50
 */
public interface EmailService {
    boolean verifyEmailCode(String email, String code);
}
