package com.gitee.whzzone.admin.service.system;

/**
 * @author : whz
 * @date : 2023/5/18 8:50
 */
public interface EmailService {
    boolean verifyEmailCode(String email, String code);
}
