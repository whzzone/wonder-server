package com.gitee.whzzone.admin.common.mybatis.strategy;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;

/**
 * @author Create by whz at 2023/8/18
 */
public interface ExpressStrategy {

    Expression apply(Object value, Column column, boolean or, Expression where);

}
