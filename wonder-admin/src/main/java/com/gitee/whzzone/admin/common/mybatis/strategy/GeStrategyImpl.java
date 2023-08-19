package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.system.pojo.dto.RuleDto;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.schema.Column;

/**
 * @author Create by whz at 2023/8/18
 */
public class GeStrategyImpl implements ExpressStrategy {

    @Override
    public Expression apply(RuleDto rule, Expression where) {
        boolean or = isOr(rule.getSpliceType());
        Column column = getColumn(rule);
        Object value = getValue(rule);
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
