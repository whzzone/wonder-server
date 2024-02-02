package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.system.pojo.dto.RuleDTO;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/8/18
 */
@Service("GT")
public class GtStrategyImpl implements ExpressStrategy{

    @Override
    public Expression apply(RuleDTO rule, Expression where) {
        Column column = getColumn(rule);
        Object value = getValue(rule);
        StringValue valueExpression = new StringValue(String.valueOf(value));
        GreaterThan greaterThan = new GreaterThan();
        greaterThan.setLeftExpression(column);
        greaterThan.setRightExpression(valueExpression);
        return assemble(rule.getSpliceType(), where, greaterThan);
    }
}
