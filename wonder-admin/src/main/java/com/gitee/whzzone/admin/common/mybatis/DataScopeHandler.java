package com.gitee.whzzone.admin.common.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.gitee.whzzone.admin.common.aspect.DataScopeAspect;
import com.gitee.whzzone.admin.common.mybatis.strategy.*;
import com.gitee.whzzone.admin.system.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.admin.system.pojo.dto.RuleDto;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.common.enums.ExpressionEnum;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
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
        expressStrategyMap.put(ExpressionEnum.EQ.toString(), new QeStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.NE.toString(), new NeStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.LIKE.toString(), new LikeStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.RIGHT_LIKE.toString(), new RightLikeStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.LEFT_LIKE.toString(), new LeftLikeStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.GT.toString(), new GtStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.GE.toString(), new GeStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.LT.toString(), new LtStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.LE.toString(), new LeStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.IN.toString(), new InStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.NOT_IN.toString(), new NotInStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.IS_NULL.toString(), new IsNullStrategyImpl());
        expressStrategyMap.put(ExpressionEnum.NOT_NULL.toString(), new NotNullStrategyImpl());
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
            ExpressStrategy expressStrategy = expressStrategyMap.get(rule.getExpression());
            if (expressStrategy == null)
                throw new IllegalArgumentException("错误的表达式：" + rule.getExpression());

            newWhere = expressStrategy.apply(rule, newWhere);
        }

        return oldWhere == null ? newWhere : new AndExpression(oldWhere, new Parenthesis(newWhere));
    }
}
