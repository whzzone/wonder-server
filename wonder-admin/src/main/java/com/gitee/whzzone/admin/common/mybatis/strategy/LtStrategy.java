package com.gitee.whzzone.admin.common.mybatis.strategy;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.schema.Column;

/**
 * @author Create by whz at 2023/8/18
 */
public class LtStrategy implements ExpressStrategy{

    @Override
    public Expression apply(Object value, Column column, boolean or, Expression where) {
        StringValue valueExpression = new StringValue((String) value);
        MinorThan minorThan = new MinorThan();
        minorThan.setLeftExpression(column);
        minorThan.setRightExpression(valueExpression);
        if (or) {
            where = where == null ? minorThan : new OrExpression(where, minorThan);
        } else {
            where = where == null ? minorThan : new AndExpression(where, minorThan);
        }
        return where;
    }
}
