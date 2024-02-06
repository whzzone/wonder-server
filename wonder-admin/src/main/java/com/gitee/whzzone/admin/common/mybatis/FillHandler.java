package com.gitee.whzzone.admin.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gitee.whzzone.admin.util.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author :whz
 * @date : 2023/5/16 23:20
 */
@Component
public class FillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        Integer userId = null;
        try {
            userId = SecurityUtil.getLoginUser().getId();
        }catch (Exception ignored){}

        strictInsertFill(metaObject,"updateTime", LocalDateTime.class, now);
        strictInsertFill(metaObject,"updateBy", Integer.class, userId);
        strictInsertFill(metaObject,"createTime", LocalDateTime.class, now);
        strictInsertFill(metaObject,"createBy", Integer.class, userId);
        strictInsertFill(metaObject,"deleted", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Integer userId = null;
        try {
            userId = SecurityUtil.getLoginUser().getId();
        }catch (Exception ignored){}

        strictUpdateFill(metaObject,"updateTime", LocalDateTime.class, LocalDateTime.now());
        strictUpdateFill(metaObject,"updateBy", Integer.class, userId);
    }
}
