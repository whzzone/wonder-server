package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.common.mybatis.DataScopeRule;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/8/18
 */
@Service("LT")
public class LtStrategyImpl implements ExpressStrategy{

    @Override
    public Expression apply(DataScopeRule rule, Expression where) {
        StringValue valueExpression = new StringValue(String.valueOf(getValue(rule)));
        MinorThan minorThan = new MinorThan();
        minorThan.setLeftExpression(getColumn(rule));
        minorThan.setRightExpression(valueExpression);
        return assemble(rule.getSpliceType(), where, minorThan);
    }
}
