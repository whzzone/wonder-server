package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.common.mybatis.DataScopeRule;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/8/18
 */
@Service("LE")
public class LeStrategyImpl implements ExpressStrategy{

    @Override
    public Expression apply(DataScopeRule rule, Expression where) {
        StringValue valueExpression = new StringValue(String.valueOf(getValue(rule)));
        MinorThanEquals minorThanEquals = new MinorThanEquals();
        minorThanEquals.setLeftExpression(getColumn(rule));
        minorThanEquals.setRightExpression(valueExpression);
        return assemble(rule.getSpliceType(), where, minorThanEquals);
    }
}
