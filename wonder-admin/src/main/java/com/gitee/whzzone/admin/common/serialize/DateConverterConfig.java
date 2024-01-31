package com.gitee.whzzone.admin.common.serialize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: bys319.com
 */
@Slf4j
@Configuration
public class DateConverterConfig {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    //不要根据提示将代码转化成lambda方式，会报错 会出现错误  Unable to determine source type <S> and target type <T> for your Converter
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConvert() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                LocalDateTime date = null;
                try {
                    date = LocalDateTime.parse(source, dateTimeFormatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }

    @Bean
    public Converter<String, LocalDate> localDateConvert() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                LocalDate date = null;
                try {
                    date = LocalDate.parse(source, dateFormatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }

    @Bean
    public Converter<String, LocalTime> localTimeConvert() {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                LocalTime date = null;
                try {
                    date = LocalTime.parse(source, timeFormatter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }

    @Bean
    public Converter<String, LocalDateTime[]> localDateTimeArrayConvert() {
        return new Converter<String, LocalDateTime[]>() {
            @Override
            public LocalDateTime[] convert(String source) {
                String[] parts = source.split(",");
                LocalDateTime[] dates = new LocalDateTime[parts.length];

                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    LocalDateTime dateTime = null;
                    try {
                        dateTime = LocalDateTime.parse(part, dateTimeFormatter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dates[i] = dateTime;
                }

                return dates;
            }
        };
    }

    @Bean
    public Converter<String, LocalDate[]> localDateArrayConvert() {
        return new Converter<String, LocalDate[]>() {
            @Override
            public LocalDate[] convert(String source) {
                String[] parts = source.split(",");
                LocalDate[] dates = new LocalDate[parts.length];

                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    LocalDate date = null;
                    try {
                        date = LocalDate.parse(part, dateFormatter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dates[i] = date;
                }

                return dates;
            }
        };
    }


    @Bean
    public Converter<String, LocalTime[]> localTimeArrayConvert() {
        return new Converter<String, LocalTime[]>() {
            @Override
            public LocalTime[] convert(String source) {
                String[] parts = source.split(",");
                LocalTime[] dates = new LocalTime[parts.length];

                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    LocalTime date = null;
                    try {
                        date = LocalTime.parse(part, timeFormatter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dates[i] = date;
                }

                return dates;
            }
        };
    }

    @Bean
    public Converter<String, List<LocalDateTime>> localDateTimeListConvert() {
        return new Converter<String, List<LocalDateTime>>() {
            @Override
            public List<LocalDateTime> convert(String source) {
                String[] parts = source.split(",");
                List<LocalDateTime> list = new ArrayList<>();

                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    LocalDateTime dateTime = null;
                    try {
                        dateTime = LocalDateTime.parse(part, dateTimeFormatter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    list.add(dateTime);
                }

                return list;
            }
        };
    }

    @Bean
    public Converter<String, List<LocalDate>> localDateListConvert() {
        return new Converter<String, List<LocalDate>>() {
            @Override
            public List<LocalDate> convert(String source) {
                String[] parts = source.split(",");
                List<LocalDate> list = new ArrayList<>();

                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    LocalDate dateTime = null;
                    try {
                        dateTime = LocalDate.parse(part, dateFormatter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    list.add(dateTime);
                }

                return list;
            }
        };
    }

    @Bean
    public Converter<String, List<LocalTime>> localTimeListConvert() {
        return new Converter<String, List<LocalTime>>() {
            @Override
            public List<LocalTime> convert(String source) {
                String[] parts = source.split(",");
                List<LocalTime> list = new ArrayList<>();

                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    LocalTime dateTime = null;
                    try {
                        dateTime = LocalTime.parse(part, timeFormatter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    list.add(dateTime);
                }

                return list;
            }
        };
    }
}