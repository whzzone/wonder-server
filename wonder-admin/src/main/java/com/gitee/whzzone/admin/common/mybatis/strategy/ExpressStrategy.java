package com.gitee.whzzone.admin.common.mybatis.strategy;

import com.gitee.whzzone.admin.system.pojo.dto.RuleDTO;
import com.gitee.whzzone.common.enums.ProvideTypeEnum;
import com.gitee.whzzone.common.enums.SpliceTypeEnum;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;

/**
 * @author Create by whz at 2023/8/18
 */
public interface ExpressStrategy {

    Expression apply(RuleDTO rule, Expression where);

    default Object getValue(RuleDTO rule) {
        if (rule.getProvideType().equals(ProvideTypeEnum.METHOD.getCode())) {
            return rule.getResult();
        } else if (rule.getProvideType().equals(ProvideTypeEnum.VALUE.getCode())) {
            return rule.getValue1();
        } else {
            throw new IllegalArgumentException("错误的提供类型");
        }
    }

    default Column getColumn(RuleDTO rule) {
        String sql = "".equals(rule.getTableAlias()) || rule.getTableAlias() == null ? rule.getColumnName() : rule.getTableAlias() + "." + rule.getColumnName();
        return new Column(sql);
    }

    default boolean isOr(String spliceType) {
        if (!spliceType.equals(SpliceTypeEnum.AND.toString()) && !spliceType.equals(SpliceTypeEnum.OR.toString())) {
            throw new IllegalArgumentException("错误的拼接类型：" + spliceType);
        }
        return spliceType.equals(SpliceTypeEnum.OR.toString());
    }

}
