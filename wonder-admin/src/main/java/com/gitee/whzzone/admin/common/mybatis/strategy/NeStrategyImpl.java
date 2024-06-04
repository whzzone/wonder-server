package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.common.mybatis.DataScopeRule;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/8/18
 */
@Service("NE")
public class NeStrategyImpl implements ExpressStrategy{

    @Override
    public Expression apply(DataScopeRule rule, Expression where) {
        StringValue valueExpression = new StringValue(String.valueOf(getValue(rule)));
        NotEqualsTo notEqualsTo = new NotEqualsTo(getColumn(rule), valueExpression);
        return assemble(rule.getSpliceType(), where, notEqualsTo);
    }
}
