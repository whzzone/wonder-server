package com.gitee.whzzone.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.gitee.whzzone.admin.system.service.EmailService;
import com.gitee.whzzone.common.util.CacheKey;
import com.gitee.whzzone.admin.common.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : whz
 * @date : 2023/5/18 8:50
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public boolean verifyEmailCode(String email, String code) {

        if (StrUtil.isBlank(email)) {
            throw new RuntimeException("邮箱为空");
        }

        if (StrUtil.isBlank(code)) {
            throw new RuntimeException("验证码为空");
        }

        String key = StrUtil.format(CacheKey.EMAIL_EMAIL_CODE, email);

        Boolean hasKey = redisCache.hasKey(key);

        if (Boolean.FALSE.equals(hasKey)) {
            throw new RuntimeException("请先获取验证码");
        }

        String cacheCode = (String) redisCache.get(key);

        return code.equals(cacheCode);
    }
}
