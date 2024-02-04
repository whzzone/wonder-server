package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.common.mybatis.DataScopeRule;
import com.gitee.whzzone.common.enums.ProvideTypeEnum;
import com.gitee.whzzone.common.enums.SpliceTypeEnum;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.schema.Column;

/**
 * @author Create by whz at 2023/8/18
 */
public interface ExpressStrategy {

    Expression apply(DataScopeRule rule, Expression where);

    /**
     * 获取条件的值
     * @param rule 某个规则
     * @return 条件的值
     */
    default Object getValue(DataScopeRule rule) {
        if (rule.getProvideType().equals(ProvideTypeEnum.METHOD.getCode())) {
            return rule.getResult();
        } else if (rule.getProvideType().equals(ProvideTypeEnum.VALUE.getCode())) {
            return rule.getValue1();
        } else {
            throw new IllegalArgumentException("错误的提供类型");
        }
    }

    /**
     * 获取字段
     * @param rule 某个规则
     * @return 字段
     */
    default Column getColumn(DataScopeRule rule) {
        String sql = "".equals(rule.getTableAlias()) || rule.getTableAlias() == null ? rule.getColumnName() : rule.getTableAlias() + "." + rule.getColumnName();
        return new Column(sql);
    }

    /**
     * 获取是否为 OR 拼接
     * @param spliceType 拼接方式
     * @return 是否为 OR 拼接
     */
    default boolean isOr(String spliceType) {
        if (!spliceType.equals(SpliceTypeEnum.AND.toString()) && !spliceType.equals(SpliceTypeEnum.OR.toString())) {
            throw new IllegalArgumentException("错误的拼接类型：" + spliceType);
        }
        return spliceType.equals(SpliceTypeEnum.OR.toString());
    }

    /**
     * 组装条件
     * @param spliceType 拼接方式
     * @param where 原来的查询条件
     * @param newExpression 新的查询条件
     * @return 组装后的查询条件
     */
    default Expression assemble(String spliceType, Expression where, Expression newExpression) {
        if (isOr(spliceType)) {
            where = where == null ? newExpression : new OrExpression(where, newExpression);
        } else {
            where = where == null ? newExpression : new AndExpression(where, newExpression);
        }
        return where;
    }

}
