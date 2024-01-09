package com.gitee.whzzone.admin.common.mybatis.strategy;

import cn.hutool.core.collection.CollectionUtil;
import com.gitee.whzzone.admin.system.pojo.dto.RuleDto;
import com.gitee.whzzone.common.enums.ProvideTypeEnum;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Create by whz at 2023/8/18
 */
public class NotInStrategyImpl implements ExpressStrategy{

    @Override
    public Expression apply(RuleDto rule, Expression where) {
        boolean or = isOr(rule.getSpliceType());
        Column column = getColumn(rule);
        Object value = getValue(rule);
        if (rule.getProvideType().equals(ProvideTypeEnum.VALUE.getCode())){
            String[] split = ((String) value).split(",");
            value = Arrays.asList(split);
        }

        if (!(value instanceof List<?>)){
            throw new RuntimeException("表达式为IN 或 NOT IN 时，反射执行方法的返回值必须是集合类型");
        }

        if (CollectionUtil.isEmpty((List<?>) value)) {
            return where;
        }

//        ExpressionList itemsList = new ExpressionList();
//
//        for (Object item : (List) value) {
//            itemsList.addExpressions(new StringValue(item.toString()));
//        }

        ExpressionList itemsList = new ExpressionList(((List<?>) value).stream().map(v -> new StringValue(v.toString())).collect(Collectors.toList()));

        InExpression inExpression = new InExpression(column, itemsList);
        NotExpression notExpression = new NotExpression(inExpression);
        if (or) {
            return where == null ? notExpression : new OrExpression(where, notExpression);
        } else {
            return where == null ? notExpression : new AndExpression(where, notExpression);
        }
    }
}
