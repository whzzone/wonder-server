package com.gitee.whzzone.admin.common.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
 
//设置Redis 缓存Key前缀
@Component
public class RedisKeySerializer implements RedisSerializer<String> {

    @Value("${spring.redis.key-prefix}")
    private String applicationName;

    /**
     * 编码格式
     */
    private final Charset charset;

    public RedisKeySerializer() {
        this(Charset.forName("UTF8"));
    }
 
    public RedisKeySerializer(Charset charset) {
        this.charset = charset;
    }
 
    /**
     * Serialize the given object to binary data.
     *
     * @return the equivalent binary data. Can be {@literal null}.
     */
    @Override
    public byte[] serialize(String cacheKey) throws SerializationException {
        String key = applicationName + cacheKey;
        return key.getBytes(charset);
    }
 
    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation. Can be {@literal null}.
     * @return the equivalent object instance. Can be {@literal null}.
     */
    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        String cacheKey = new String(bytes, charset);
        int indexOf = cacheKey.indexOf(applicationName);
        if (indexOf == -1) {
            cacheKey = applicationName + cacheKey;
        }
        return (cacheKey.getBytes() == null ? null : cacheKey);
    }
}