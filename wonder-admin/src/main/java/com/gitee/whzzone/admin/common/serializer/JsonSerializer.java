//package com.example.securitytest.common;
//
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.util.Objects;
//
//@Configuration
//public class JsonSerializerConfig  {
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//        SimpleModule simpleModule = new SimpleModule();
//        // Long类型转字符串
//        simpleModule.addSerializer(Long.class, new JsonSerializer<Long>() {
//            @Override
//            public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//                if (Objects.isNull(value)) {
//                    gen.writeString("");
//                }else {
//                    gen.writeString(value.toString());
//                }
//            }
//        });
//
//        objectMapper.registerModule(simpleModule);
//        return objectMapper;
//    }
//}