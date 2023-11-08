package com.gitee.whzzone.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Objects;

/**
 * @author Create by whz at 2023/7/10
 */
public class ListLongSerializer  extends JsonSerializer<List<Long>> {

    @SneakyThrows
    @Override
    public void serialize(List<Long> value, JsonGenerator gen, SerializerProvider serializers) {
        if (Objects.isNull(value)) {
            gen.writeStartArray();
            gen.writeEndArray();
        } else {
            gen.writeStartArray();
            for (Long item : value) {
                String plainString = (item != null) ? item.toString() : "";
                gen.writeString(plainString);
            }
            gen.writeEndArray();
        }
    }
}
