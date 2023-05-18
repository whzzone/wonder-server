package com.example.securitytest;

import com.example.securitytest.pojo.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootTest
class SecurityTestApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void set() {
        SysUser sysUser = new SysUser();
        sysUser.setId(12345687979L);
        sysUser.setNickname("你好");
        sysUser.setUpdateTime(new Date());
        redisTemplate.boundValueOps("test").set(sysUser);
        redisTemplate.boundValueOps("test2").set("你好");
    }

    @Test
    void get() {
        SysUser sysUser = (SysUser) redisTemplate.boundValueOps("test").get();
        System.out.println("sysUser = " + sysUser);
    }
}
