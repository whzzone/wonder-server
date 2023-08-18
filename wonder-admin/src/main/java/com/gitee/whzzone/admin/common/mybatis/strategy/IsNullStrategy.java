package com.gitee.whzzone.admin.common.mybatis.strategy;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.schema.Column;

/**
 * @author Create by whz at 2023/8/18
 */
public class IsNullStrategy implements ExpressStrategy{

    @Override
    public Expression apply(Object value, Column column, boolean or, Expression where) {
        IsNullExpression isNullExpression = new IsNullExpression();
        isNullExpression.setLeftExpression(column);
        if (or) {
            where = where == null ? isNullExpression : new OrExpression(where, isNullExpression);
        } else {
            where = where == null ? isNullExpression : new AndExpression(where, isNullExpression);
        }
        return where;
    }
}
