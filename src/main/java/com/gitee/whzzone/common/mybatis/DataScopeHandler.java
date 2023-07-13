package com.gitee.whzzone.common.mybatis;

import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.gitee.whzzone.common.aspect.DataScopeAspect;
import com.gitee.whzzone.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Create by whz at 2023/6/8
 */
@Slf4j
@Component
public class DataScopeHandler implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        DataScopeAspect.DataScopeParam dataScopeParam = DataScopeAspect.getDataScopeParam();
        if (dataScopeParam == null || SecurityUtil.isAdmin()) {
            return where;
        }
        String sql = "".equals(dataScopeParam.getTableAlias()) || dataScopeParam.getTableAlias() == null ? dataScopeParam.getField() : dataScopeParam.getTableAlias() + "." + dataScopeParam.getField();
        ItemsList itemsList = new ExpressionList(dataScopeParam.getSecretary().stream().map(LongValue::new).collect(Collectors.toList()));
        InExpression inExpression = new InExpression(new Column(sql), itemsList);

        return where == null ? inExpression : new AndExpression(where, inExpression);
    }

}
