package com.example.securitytest;

import cn.hutool.extra.mail.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SecurityTestApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        MailUtil.send("499424437@qq.com", "测试", "邮件来自Hutool测试", false);

    }

}
