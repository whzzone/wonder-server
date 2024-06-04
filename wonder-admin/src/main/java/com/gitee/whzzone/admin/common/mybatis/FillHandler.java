package com.gitee.whzzone.admin.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gitee.whzzone.admin.util.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author :whz
 * @date : 2023/5/16 23:20
 */
@Component
public class FillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        Integer userId = null;
        try {
            userId = SecurityUtil.getLoginUser().getId();
        }catch (Exception ignored){}

        strictInsertFill(metaObject,"updateTime", Date.class, now);
        strictInsertFill(metaObject,"updateBy", Integer.class, userId);
        strictInsertFill(metaObject,"createTime", Date.class, now);
        strictInsertFill(metaObject,"createBy", Integer.class, userId);
        strictInsertFill(metaObject,"deleted", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Integer userId = null;
        try {
            userId = SecurityUtil.getLoginUser().getId();
        }catch (Exception ignored){}

        strictUpdateFill(metaObject,"updateTime", Date.class, new Date());
        strictUpdateFill(metaObject,"updateBy", Integer.class, userId);
    }
}
