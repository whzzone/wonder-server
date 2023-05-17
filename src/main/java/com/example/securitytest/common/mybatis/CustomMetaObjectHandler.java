package com.example.securitytest.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.securitytest.util.SecurityUtil;
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
        Date now = new Date();
        strictInsertFill(metaObject,"updateTime", Date.class, now);
        strictInsertFill(metaObject,"updateBy", Long.class, SecurityUtil.getLoginUser().getId());
        strictInsertFill(metaObject,"createTime", Date.class, now);
        strictInsertFill(metaObject,"createBy", Long.class, SecurityUtil.getLoginUser().getId());
        strictInsertFill(metaObject,"deleted", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictInsertFill(metaObject,"updateTime", Date.class, new Date());
        strictInsertFill(metaObject,"updateBy", Long.class, SecurityUtil.getLoginUser().getId());
    }
}
