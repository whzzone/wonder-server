package com.gitee.whzzone.admin.common.mybatis.strategy;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.schema.Column;

/**
 * @author Create by whz at 2023/8/18
 */
public class GeStrategy implements ExpressStrategy{

    @Override
    public Expression apply(Object value, Column column, boolean or, Expression where) {
        StringValue valueExpression = new StringValue((String) value);
        GreaterThanEquals greaterThanEquals = new GreaterThanEquals();
        greaterThanEquals.setLeftExpression(column);
        greaterThanEquals.setRightExpression(valueExpression);
        if (or) {
            where = where == null ? greaterThanEquals : new OrExpression(where, greaterThanEquals);
        } else {
            where = where == null ? greaterThanEquals : new AndExpression(where, greaterThanEquals);
        }
        return where;
    }
}
