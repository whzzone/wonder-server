package com.gitee.whzzone.admin.common.mybatis.strategy;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;

/**
 * @author Create by whz at 2023/8/18
 */
public class NeStrategy implements ExpressStrategy{

    @Override
    public Expression apply(Object value, Column column, boolean or, Expression where) {
        StringValue valueExpression = new StringValue((String) value);
        NotEqualsTo notEqualsTo = new NotEqualsTo(column, valueExpression);
        if (or) {
            where = where == null ? notEqualsTo : new OrExpression(where, notEqualsTo);
        } else {
            where = where == null ? notEqualsTo : new AndExpression(where, notEqualsTo);
        }
        return where;
    }
}
