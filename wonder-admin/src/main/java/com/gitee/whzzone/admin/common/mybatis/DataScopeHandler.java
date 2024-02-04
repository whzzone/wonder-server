package com.gitee.whzzone.admin.common.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.gitee.whzzone.admin.common.aspect.DataScopeAspect;
import com.gitee.whzzone.admin.common.mybatis.strategy.ExpressStrategy;
import com.gitee.whzzone.admin.system.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.admin.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 数据权限处理器
 * Create by whz at 2023/6/8
 */
@Slf4j
@Component
public class DataScopeHandler implements DataPermissionHandler {

    @Autowired
    Map<String, ExpressStrategy> expressStrategyMap;

    private final Expression noDataExpression = getNoDataExpression();

    @Override
    public Expression getSqlSegment(Expression oldWhere, String mappedStatementId) {
        DataScopeInfo dataScopeInfo = DataScopeAspect.getDataScopeInfo();
        // 没有规则 或 或者管理员 就不限制
        // 这个有待考虑实际情况修改
        // 情况1: 没有配置规则的话就看不到任何数据
        // 情况2: 没有配置规则的话就不限制
        // 当前是 情况2
        if (dataScopeInfo == null
                || CollectionUtil.isEmpty(dataScopeInfo.getRuleList())
                || SecurityUtil.isAdmin()
        ) {
            return oldWhere;
        }

        log.debug("----------------------------------数据权限处理器 开始处理SQL----------------------------------");
        log.debug("处理前的 WHERE 条件: {}", oldWhere.toString());
        Expression newWhere = null;

        List<DataScopeRule> ruleList = dataScopeInfo.getRuleList();

        // 当规则只有一条且需要根据规则构造的条件为空时, 让这条sql无法查询出数据
        if (ruleList.size() == 1) {
            Expression apply = process(ruleList.get(0), newWhere);
            newWhere = Objects.isNull(apply) ? this.noDataExpression : apply;

        }else {
            for (DataScopeRule rule : ruleList) {
                Expression apply = process(rule, newWhere);
                if (!Objects.isNull(apply)) {
                    newWhere = apply;
                }
            }
        }

        log.debug("数据限制的 WHERE 条件: {}", newWhere);

        newWhere = new AndExpression(oldWhere, new Parenthesis(newWhere));

        log.debug("处理后的 WHERE 条件: {}", newWhere);
        log.debug("----------------------------------数据权限处理器 处理SQL结束----------------------------------");

        return newWhere;
    }

    private Expression process(DataScopeRule rule, Expression expression) {
        ExpressStrategy strategy = expressStrategyMap.get(rule.getExpression());
        if (strategy == null)
            throw new IllegalArgumentException("错误的表达式：" + rule.getExpression());

        return strategy.apply(rule, expression);
    }

    private Expression getNoDataExpression() {
        // 构造 1 != 1
        LongValue value = new LongValue(1);
        NotEqualsTo notEqualsTo = new NotEqualsTo();
        notEqualsTo.setLeftExpression(value);
        notEqualsTo.setRightExpression(value);
        return notEqualsTo;
    }
}
