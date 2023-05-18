package com.example.securitytest.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.securitytest.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : whz
 * @date : 2023/5/18 8:50
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean verifyEmailCode(String email, String code) {

        if (StrUtil.isBlank(email)){
            throw new RuntimeException("邮箱为空");
        }

        if (StrUtil.isBlank(code)){
            throw new RuntimeException("验证码为空");
        }

        Boolean hasKey = redisTemplate.hasKey("EMAIL:" + email);

        if (Boolean.FALSE.equals(hasKey)){
            throw new RuntimeException("请先获取验证码");
        }

        String cacheCode = (String) redisTemplate.opsForValue().get("EMAIL:" + email);

        return code.equals(cacheCode);
    }
}
