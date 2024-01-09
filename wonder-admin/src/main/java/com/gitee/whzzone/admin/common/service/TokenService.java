package com.gitee.whzzone.admin.common.service;

import cn.hutool.core.util.StrUtil;
import com.gitee.whzzone.admin.common.properties.SecurityProperties;
import com.gitee.whzzone.admin.common.redis.RedisCache;
import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.common.constant.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Create by whz at 2024/1/3
 */
@Component
public class TokenService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 生成令牌
     * @param loginUser 要封装的信息
     * @return 生成的令牌
     */
    public String createToken(LoginUser loginUser) {
        String token = UUID.randomUUID().toString();
        loginUser.setToken(token);
        return token;
    }

    /**
     * 缓存令牌
     * @param loginUser 封装信息
     */
    public void cacheToken(LoginUser loginUser) {
        redisCache.set(getTokenKey(loginUser.getToken()), loginUser, securityProperties.getToken().getLiveTime(), securityProperties.getToken().getLiveUnit());
    }

    /**
     * 根据token获取用户信息
     * @param token 令牌
     * @return 登录的用户信息
     */
    public LoginUser getLoginUser(String token) {
        String tokenKey = getTokenKey(token);
        if (Boolean.FALSE.equals(redisCache.hasKey(tokenKey)))
            throw new RuntimeException("无效的token");

        return (LoginUser) redisCache.get(tokenKey);
    }

    @Async
    public void renewToken(String token) {
        String tokenKey = getTokenKey(token);

        if (redisCache.getExpire(tokenKey) < securityProperties.getToken().getRefreshUnit().toSeconds(securityProperties.getToken().getRefreshTime())) {
            redisCache.expire(tokenKey, securityProperties.getToken().getLiveTime(), securityProperties.getToken().getLiveUnit());
        }

    }

    public String getTokenKey(String token) {
        return StrUtil.format(CommonConstants.TOKEN_CACHE_KEY, token);
    }
}
