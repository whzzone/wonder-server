package com.example.securitytest.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author :whz
 * @date : 2023/5/16 23:20
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        strictInsertFill(metaObject,"updateTime", Date.class, new Date());
        strictInsertFill(metaObject,"createTime", Date.class, new Date());
        strictInsertFill(metaObject,"deleted", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictInsertFill(metaObject,"updateTime", Date.class, new Date());
    }
}
