package com.gitee.whzzone.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

import java.util.Objects;

/**
 * 自定义Long序列化器
 */
public class LongSerializer extends JsonSerializer<Long> {
 
    @SneakyThrows
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) {
        if (Objects.isNull(value)){
            gen.writeString("");
        }else {
            String plainString = value.toString();
            gen.writeString(plainString);
        }
    }
}