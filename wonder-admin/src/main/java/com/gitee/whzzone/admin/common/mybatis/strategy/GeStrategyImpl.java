package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.system.pojo.dto.RuleDTO;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/8/18
 */
@Service("GE")
public class GeStrategyImpl implements ExpressStrategy {

    @Override
    public Expression apply(RuleDTO rule, Expression where) {
        Column column = getColumn(rule);
        Object value = getValue(rule);
        StringValue valueExpression = new StringValue(String.valueOf(value));
        GreaterThanEquals greaterThanEquals = new GreaterThanEquals();
        greaterThanEquals.setLeftExpression(column);
        greaterThanEquals.setRightExpression(valueExpression);
        return assemble(rule.getSpliceType(), where, greaterThanEquals);
    }
}
