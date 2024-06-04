package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.common.mybatis.DataScopeRule;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/8/18
 */
@Service("LIKE")
public class LikeStrategyImpl implements ExpressStrategy{

    @Override
    public Expression apply(DataScopeRule rule, Expression where) {
        StringValue valueExpression = new StringValue("%" + getValue(rule) + "%");
        LikeExpression likeExpression = new LikeExpression();
        likeExpression.setLeftExpression(getColumn(rule));
        likeExpression.setRightExpression(valueExpression);
        return assemble(rule.getSpliceType(), where, likeExpression);
    }
}
