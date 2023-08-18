package com.gitee.whzzone.admin.common.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.gitee.whzzone.admin.common.aspect.DataScopeAspect;
import com.gitee.whzzone.admin.common.mybatis.strategy.*;
import com.gitee.whzzone.admin.system.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.admin.system.pojo.dto.RuleDto;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.common.enums.ExpressionEnum;
import com.gitee.whzzone.common.enums.ProvideTypeEnum;
import com.gitee.whzzone.common.enums.SpliceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by whz at 2023/6/8
 */
@Slf4j
@Component
public class DataScopeHandler implements DataPermissionHandler {

    Map<String, ExpressStrategy> expressStrategyMap = new HashMap<>();

    @PostConstruct
    public void init() {
        expressStrategyMap.put(ExpressionEnum.EQ.toString(), new QeStrategy());
        expressStrategyMap.put(ExpressionEnum.NE.toString(), new NeStrategy());
        expressStrategyMap.put(ExpressionEnum.LIKE.toString(), new LikeStrategy());
        expressStrategyMap.put(ExpressionEnum.RIGHT_LIKE.toString(), new RightLikeStrategy());
        expressStrategyMap.put(ExpressionEnum.LEFT_LIKE.toString(), new LeftLikeStrategy());
        expressStrategyMap.put(ExpressionEnum.GT.toString(), new GtStrategy());
        expressStrategyMap.put(ExpressionEnum.GE.toString(), new GeStrategy());
        expressStrategyMap.put(ExpressionEnum.LT.toString(), new LtStrategy());
        expressStrategyMap.put(ExpressionEnum.LE.toString(), new LeStrategy());
        expressStrategyMap.put(ExpressionEnum.IN.toString(), new InStrategy());
        expressStrategyMap.put(ExpressionEnum.NOT_IN.toString(), new NotInStrategy());
        expressStrategyMap.put(ExpressionEnum.IS_NULL.toString(), new IsNullStrategy());
        expressStrategyMap.put(ExpressionEnum.NOT_NULL.toString(), new NotNullStrategy());
        // TODO 还差一个 BETWEEN
    }

    @Override
    public Expression getSqlSegment(Expression oldWhere, String mappedStatementId) {
        DataScopeAspect.DataScopeParam dataScopeParam = DataScopeAspect.getDataScopeParam();
        // 没有规则就不限制
        if (dataScopeParam == null || dataScopeParam.getDataScopeInfo() == null || CollectionUtil.isEmpty(dataScopeParam.getDataScopeInfo().getRuleList()) || SecurityUtil.isAdmin()) {
            return oldWhere;
        }

        Expression newWhere = null;

        DataScopeInfo dataScopeInfo = dataScopeParam.getDataScopeInfo();
        List<RuleDto> ruleList = dataScopeInfo.getRuleList();
        for (RuleDto rule : ruleList) {
            String sql = "".equals(rule.getTableAlias()) || rule.getTableAlias() == null ? rule.getColumnName() : rule.getTableAlias() + "." + rule.getColumnName();

            Column column = new Column(sql);

            Object value;

            if (rule.getProvideType().equals(ProvideTypeEnum.METHOD.getCode())) {
                value = rule.getResult();
            } else if (rule.getProvideType().equals(ProvideTypeEnum.VALUE.getCode())) {
                value = rule.getValue1();
            } else {
                throw new IllegalArgumentException("错误的提供类型");
            }

            if (!rule.getSpliceType().equals(SpliceTypeEnum.AND.toString()) && !rule.getSpliceType().equals(SpliceTypeEnum.OR.toString())) {
                throw new IllegalArgumentException("错误的拼接类型：" + rule.getSpliceType());
            }

            boolean or = rule.getSpliceType().equals(SpliceTypeEnum.OR.toString());

            ExpressStrategy expressStrategy = expressStrategyMap.get(rule.getExpression());
            if (expressStrategy == null)
                throw new IllegalArgumentException("错误的表达式：" + rule.getExpression());

            newWhere = expressStrategy.apply(value, column, or, newWhere);

        }
        return new AndExpression(oldWhere, new Parenthesis(newWhere));
    }
}
