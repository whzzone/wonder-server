package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.common.mybatis.DataScopeRule;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/8/18
 */
@Service("NOT_NULL")
public class NotNullStrategyImpl implements ExpressStrategy {

    @Override
    public Expression apply(DataScopeRule rule, Expression where) {
        IsNullExpression isNullExpression = new IsNullExpression();
        isNullExpression.setNot(true);
        isNullExpression.setLeftExpression(getColumn(rule));
        return assemble(rule.getSpliceType(), where, isNullExpression);
    }
}
