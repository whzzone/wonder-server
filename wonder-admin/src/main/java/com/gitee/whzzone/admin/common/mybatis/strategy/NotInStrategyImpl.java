package com.gitee.whzzone.admin.common.mybatis.strategy;

import cn.hutool.core.collection.CollectionUtil;
import com.gitee.whzzone.admin.system.pojo.dto.RuleDto;
import com.gitee.whzzone.common.enums.ProvideTypeEnum;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
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

        if (value instanceof List<?> && CollectionUtil.isNotEmpty((List<?>) value)){
            ItemsList itemsList = null;

            Object o = ((List<?>) value).get(0);
            if (o instanceof String) {
                itemsList = new ExpressionList(((List<String>) value).stream().map(StringValue::new).collect(Collectors.toList()));
            } else if (o instanceof Long) {
                itemsList = new ExpressionList(((List<Long>) value).stream().map(LongValue::new).collect(Collectors.toList()));
            }

            InExpression inExpression = new InExpression(column, itemsList);
            NotExpression notExpression = new NotExpression(inExpression);
            if (or) {
                where = where == null ? notExpression : new OrExpression(where, notExpression);
            } else {
                where = where == null ? notExpression : new AndExpression(where, notExpression);
            }
        } else {
            throw new RuntimeException("表达式为IN 或 NOT IN 时，反射执行方法的返回值必须是集合类型");
        }
        return where;
    }
}