package com.gitee.whzzone.admin.common.mybatis.strategy;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;

/**
 * @author Create by whz at 2023/8/18
 */
public class QeStrategy implements ExpressStrategy{

    @Override
    public Expression apply(Object value, Column column, boolean or, Expression where) {
        StringValue valueExpression = new StringValue((String) value);
        EqualsTo equalsTo = new EqualsTo(column, valueExpression);
        if (or) {
            where = where == null ? equalsTo : new OrExpression(where, equalsTo);
        } else {
            where = where == null ? equalsTo : new AndExpression(where, equalsTo);
        }
        return where;
    }
}
