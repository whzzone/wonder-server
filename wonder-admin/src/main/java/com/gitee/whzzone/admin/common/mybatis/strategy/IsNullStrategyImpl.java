package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.system.pojo.dto.RuleDTO;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/8/18
 */
@Service("IS_NULL")
public class IsNullStrategyImpl implements ExpressStrategy{

    @Override
    public Expression apply(RuleDTO rule, Expression where) {
        boolean or = isOr(rule.getSpliceType());
        Column column = getColumn(rule);
        IsNullExpression isNullExpression = new IsNullExpression();
        isNullExpression.setLeftExpression(column);
        if (or) {
            where = where == null ? isNullExpression : new OrExpression(where, isNullExpression);
        } else {
            where = where == null ? isNullExpression : new AndExpression(where, isNullExpression);
        }
        return where;
    }
}
