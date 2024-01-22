package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.system.pojo.dto.RuleDTO;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/8/18
 */
@Service("NE")
public class NeStrategyImpl implements ExpressStrategy{

    @Override
    public Expression apply(RuleDTO rule, Expression where) {
        boolean or = isOr(rule.getSpliceType());
        Column column = getColumn(rule);
        Object value = getValue(rule);
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
