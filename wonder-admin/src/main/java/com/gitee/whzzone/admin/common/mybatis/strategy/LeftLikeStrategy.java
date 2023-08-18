package com.gitee.whzzone.admin.common.mybatis.strategy;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.schema.Column;

/**
 * @author Create by whz at 2023/8/18
 */
public class LeftLikeStrategy implements ExpressStrategy{

    @Override
    public Expression apply(Object value, Column column, boolean or, Expression where) {
        StringValue valueExpression = new StringValue("%" + value);
        LikeExpression likeExpression = new LikeExpression();
        likeExpression.setLeftExpression(column);
        likeExpression.setRightExpression(valueExpression);
        if (or) {
            where = where == null ? likeExpression : new OrExpression(where, likeExpression);
        } else {
            where = where == null ? likeExpression : new AndExpression(where, likeExpression);
        }
        return where;
    }
}
