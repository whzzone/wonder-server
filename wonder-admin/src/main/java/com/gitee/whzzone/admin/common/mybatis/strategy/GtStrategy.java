package com.gitee.whzzone.admin.common.mybatis.strategy;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.schema.Column;

/**
 * @author Create by whz at 2023/8/18
 */
public class GtStrategy implements ExpressStrategy{

    @Override
    public Expression apply(Object value, Column column, boolean or, Expression where) {
        StringValue valueExpression = new StringValue((String) value);
        GreaterThan greaterThan = new GreaterThan();
        greaterThan.setLeftExpression(column);
        greaterThan.setRightExpression(valueExpression);
        if (or) {
            where = where == null ? greaterThan : new OrExpression(where, greaterThan);
        } else {
            where = where == null ? greaterThan : new AndExpression(where, greaterThan);
        }
        return where;
    }
}
