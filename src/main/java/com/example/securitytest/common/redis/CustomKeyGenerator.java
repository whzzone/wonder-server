package com.example.securitytest.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author : whz
 * @date : 2023/5/19 8:47
 */
@Component
@Slf4j
public class CustomKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuffer sb = new StringBuffer();
//        sb.append(target.getClass().getSimpleName());
//        sb.append(":");
        sb.append(method.getName());
        sb.append(":");

        for (int i = 0; i < params.length; i++) {
            sb.append(params[i].toString());
            if (i != params.length - 1){
                sb.append(":");
            }
        }

        log.warn(sb.toString());
        return sb.toString();
    }
}
