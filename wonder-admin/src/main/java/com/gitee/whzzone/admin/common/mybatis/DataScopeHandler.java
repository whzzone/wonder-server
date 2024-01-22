package com.gitee.whzzone.admin.common.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.gitee.whzzone.admin.common.aspect.DataScopeAspect;
import com.gitee.whzzone.admin.common.mybatis.strategy.ExpressStrategy;
import com.gitee.whzzone.admin.system.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.admin.system.pojo.dto.RuleDTO;
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

/**
 * Create by whz at 2023/6/8
 */
@Slf4j
@Component
public class DataScopeHandler implements DataPermissionHandler {

    @Autowired
    Map<String, ExpressStrategy> expressStrategyMap;

    @Override
    public Expression getSqlSegment(Expression oldWhere, String mappedStatementId) {
        DataScopeInfo dataScopeInfo = DataScopeAspect.getDataScopeInfo();
        // 没有规则就不限制
        if (dataScopeInfo == null
                || CollectionUtil.isEmpty(dataScopeInfo.getRuleList())
                || SecurityUtil.isAdmin()
        ) {
            return oldWhere;
        }

        Expression newWhere = null;

        List<RuleDTO> ruleList = dataScopeInfo.getRuleList();
        for (RuleDTO rule : ruleList) {
            ExpressStrategy expressStrategy = expressStrategyMap.get(rule.getExpression());
            if (expressStrategy == null)
                throw new IllegalArgumentException("错误的表达式：" + rule.getExpression());

            Expression apply = expressStrategy.apply(rule, newWhere);

            if (ruleList.size() == 1) {
                if (apply == null) {
                    // 构造 1 != 1, 让这条sql无法查询出数据
                    LongValue value = new LongValue(1);
                    NotEqualsTo notEqualsTo = new NotEqualsTo();
                    notEqualsTo.setLeftExpression(value);
                    notEqualsTo.setRightExpression(value);
                    newWhere = notEqualsTo;
                }else {
                    newWhere = apply;
                }
            }else {
                if (apply != null) {
                    newWhere = expressStrategy.apply(rule, newWhere);
                }
            }
        }

        return oldWhere == null ? newWhere : new AndExpression(oldWhere, new Parenthesis(newWhere));
    }
}
