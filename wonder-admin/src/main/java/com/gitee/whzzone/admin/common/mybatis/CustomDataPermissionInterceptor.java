package com.gitee.whzzone.admin.common.mybatis;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import lombok.*;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;

import java.sql.Connection;

/**
 * 自定义数据权限拦截器，支持查询、更新、删除
 * @author Create by whz at 2024/4/16
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CustomDataPermissionInterceptor extends DataPermissionInterceptor {

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
            if (InterceptorIgnoreHelper.willIgnoreDataPermission(ms.getId())) {
                return;
            }
            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            mpBs.sql(parserMulti(mpBs.sql(), null));
        }
    }

    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        Expression newWhere = getDataPermissionHandler().getSqlSegment(update.getWhere(), (String) obj);
        update.setWhere(newWhere);
    }

    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {

    }

    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        Expression newWhere = getDataPermissionHandler().getSqlSegment(delete.getWhere(), (String) obj);
        delete.setWhere(newWhere);
    }

    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) {
        if (InterceptorIgnoreHelper.willIgnoreDataPermission(ms.getId())) return;
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(ms.getBoundSql(parameter));
        mpBs.sql(parserSingle(mpBs.sql(), ms.getId()));
    }
}
