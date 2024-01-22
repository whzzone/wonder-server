package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.system.pojo.dto.RuleDTO;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/8/18
 */
@Service("RIGHT_LIKE")
public class RightLikeStrategyImpl implements ExpressStrategy{

    @Override
    public Expression apply(RuleDTO rule, Expression where) {
        boolean or = isOr(rule.getSpliceType());
        Column column = getColumn(rule);
        Object value = getValue(rule);
        StringValue valueExpression = new StringValue(value + "%");
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
