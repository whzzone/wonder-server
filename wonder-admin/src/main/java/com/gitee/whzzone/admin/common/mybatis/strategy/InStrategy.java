package com.gitee.whzzone.admin.common.mybatis.strategy;

import cn.hutool.core.collection.CollectionUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Create by whz at 2023/8/18
 */
public class InStrategy implements ExpressStrategy{

    @Override
    public Expression apply(Object value, Column column, boolean or, Expression where) {
        if (value instanceof List<?> && CollectionUtil.isNotEmpty((List<?>) value)) {
            ItemsList itemsList = null;
            Object o = ((List<?>) value).get(0);
            if (o instanceof String) {
                itemsList = new ExpressionList(((List<String>) value).stream().map(StringValue::new).collect(Collectors.toList()));
            } else if (o instanceof Long) {
                itemsList = new ExpressionList(((List<Long>) value).stream().map(LongValue::new).collect(Collectors.toList()));
            }

            InExpression inExpression = new InExpression(column, itemsList);
            if (or) {
                where = where == null ? inExpression : new OrExpression(where, inExpression);
            } else {
                where = where == null ? inExpression : new AndExpression(where, inExpression);
            }

        } else {
            throw new RuntimeException("表达式为IN 或 NOT IN 时，反射执行方法的返回值必须是集合类型");
        }
        return where;
    }
}
