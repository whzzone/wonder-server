package com.gitee.whzzone.common.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.gitee.whzzone.common.aspect.DataScopeAspect;
import com.gitee.whzzone.common.enums.ExpressionEnum;
import com.gitee.whzzone.common.enums.ProvideTypeEnum;
import com.gitee.whzzone.common.enums.SpliceTypeEnum;
import com.gitee.whzzone.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.pojo.dto.RuleDto;
import com.gitee.whzzone.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by whz at 2023/6/8
 */
@Slf4j
@Component
public class DataScopeHandler implements DataPermissionHandler {

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        DataScopeAspect.DataScopeParam dataScopeParam = DataScopeAspect.getDataScopeParam();
        if (dataScopeParam == null || SecurityUtil.isAdmin()) {
            return where;
        }

        DataScopeInfo dataScopeInfo = dataScopeParam.getDataScopeInfo();
        RuleDto dto = dataScopeInfo.getDto();
        List<Long> idList = dataScopeInfo.getIdList();
        String sql = "".equals(dto.getTableAlias()) || dto.getTableAlias() == null ? dto.getColumnName() : dto.getTableAlias() + "." + dto.getColumnName();

        if (dto.getProvideType().equals(ProvideTypeEnum.METHOD.getCode())) {
            if (CollectionUtil.isEmpty(idList))
                throw new RuntimeException("没有查看权限");

            ItemsList itemsList = new ExpressionList(idList.stream().map(LongValue::new).collect(Collectors.toList()));
            InExpression inExpression = new InExpression(new Column(sql), itemsList);
            if (dto.getExpression().equals(ExpressionEnum.IN.toString())) {
                if (dto.getSpliceType().equals(SpliceTypeEnum.OR.toString())) {
                    return where == null ? inExpression : new OrExpression(where, inExpression);
                } else if (dto.getSpliceType().equals(SpliceTypeEnum.AND.toString())) {
                    return where == null ? inExpression : new AndExpression(where, inExpression);
                } else
                    throw new RuntimeException("错误的拼接类型：" + dto.getSpliceType());

            } else if (dto.getExpression().equals(ExpressionEnum.NOTIN.toString())) {
                NotExpression notExpression = new NotExpression(inExpression);

                if (dto.getSpliceType().equals(SpliceTypeEnum.OR.toString())) {
                    return where == null ? notExpression : new OrExpression(where, notExpression);
                } else if (dto.getSpliceType().equals(SpliceTypeEnum.AND.toString())) {
                    return where == null ? notExpression : new AndExpression(where, notExpression);
                } else
                    throw new RuntimeException("错误的拼接类型：" + dto.getSpliceType());
            } else
                throw new RuntimeException("错误的表达式：" + dto.getExpression());

        } else if (dto.getProvideType().equals(ProvideTypeEnum.VALUE.getCode())) {
            if (dto.getExpression().equals(ExpressionEnum.EQ.toString())) {
                StringValue valueExpression = new StringValue(dto.getValue1());
                EqualsTo equalsTo = new EqualsTo(new Column(sql), valueExpression);

                if (dto.getSpliceType().equals(SpliceTypeEnum.OR.toString())) {
                    return where == null ? equalsTo : new OrExpression(where, equalsTo);
                } else if (dto.getSpliceType().equals(SpliceTypeEnum.AND.toString())) {
                    return where == null ? equalsTo : new AndExpression(where, equalsTo);
                } else
                    throw new RuntimeException("错误的拼接类型：" + dto.getSpliceType());

            } else if (dto.getExpression().equals(ExpressionEnum.NE.toString())) {
                StringValue valueExpression = new StringValue(dto.getValue1());
                NotEqualsTo notEqualsTo = new NotEqualsTo(new Column(sql), valueExpression);

                if (dto.getSpliceType().equals(SpliceTypeEnum.OR.toString())) {
                    return where == null ? notEqualsTo : new OrExpression(where, notEqualsTo);
                } else if (dto.getSpliceType().equals(SpliceTypeEnum.AND.toString())) {
                    return where == null ? notEqualsTo : new AndExpression(where, notEqualsTo);
                } else
                    throw new RuntimeException("错误的拼接类型：" + dto.getSpliceType());

            } else if (dto.getExpression().equals(ExpressionEnum.LIKE.toString())) {
                StringValue valueExpression = new StringValue("%" + dto.getValue1() + "%");
                LikeExpression likeExpression = new LikeExpression();
                likeExpression.setLeftExpression(new Column(sql));
                likeExpression.setRightExpression(valueExpression);

                if (dto.getSpliceType().equals(SpliceTypeEnum.OR.toString())) {
                    return where == null ? likeExpression : new OrExpression(where, likeExpression);
                } else if (dto.getSpliceType().equals(SpliceTypeEnum.AND.toString())) {
                    return where == null ? likeExpression : new AndExpression(where, likeExpression);
                } else
                    throw new RuntimeException("错误的拼接类型：" + dto.getSpliceType());

            } else if (dto.getExpression().equals(ExpressionEnum.GT.toString())) {
                StringValue valueExpression = new StringValue(dto.getValue1());
                Column column = new Column(dto.getColumnName());

                GreaterThan greaterThan = new GreaterThan();
                greaterThan.setLeftExpression(column);
                greaterThan.setRightExpression(valueExpression);

                if (dto.getSpliceType().equals(SpliceTypeEnum.OR.toString())) {
                    return where == null ? greaterThan : new OrExpression(where, greaterThan);
                } else if (dto.getSpliceType().equals(SpliceTypeEnum.AND.toString())) {
                    return where == null ? greaterThan : new AndExpression(where, greaterThan);
                } else
                    throw new RuntimeException("错误的拼接类型：" + dto.getSpliceType());

            } else if (dto.getExpression().equals(ExpressionEnum.GE.toString())) {
                StringValue valueExpression = new StringValue(dto.getValue1());
                Column column = new Column(dto.getColumnName());

                GreaterThanEquals greaterThanEquals = new GreaterThanEquals();
                greaterThanEquals.setLeftExpression(column);
                greaterThanEquals.setRightExpression(valueExpression);

                if (dto.getSpliceType().equals(SpliceTypeEnum.OR.toString())) {
                    return where == null ? greaterThanEquals : new OrExpression(where, greaterThanEquals);
                } else if (dto.getSpliceType().equals(SpliceTypeEnum.AND.toString())) {
                    return where == null ? greaterThanEquals : new AndExpression(where, greaterThanEquals);
                } else
                    throw new RuntimeException("错误的拼接类型：" + dto.getSpliceType());

            } else if (dto.getExpression().equals(ExpressionEnum.LT.toString())) {
                StringValue valueExpression = new StringValue(dto.getValue1());
                Column column = new Column(dto.getColumnName());

                MinorThan minorThan = new MinorThan();
                minorThan.setLeftExpression(column);
                minorThan.setRightExpression(valueExpression);

                if (dto.getSpliceType().equals(SpliceTypeEnum.OR.toString())) {
                    return where == null ? minorThan : new OrExpression(where, minorThan);
                } else if (dto.getSpliceType().equals(SpliceTypeEnum.AND.toString())) {
                    return where == null ? minorThan : new AndExpression(where, minorThan);
                } else
                    throw new RuntimeException("错误的拼接类型：" + dto.getSpliceType());

            } else if (dto.getExpression().equals(ExpressionEnum.LE.toString())) {
                StringValue valueExpression = new StringValue(dto.getValue1());
                Column column = new Column(dto.getColumnName());

                MinorThanEquals minorThanEquals = new MinorThanEquals();
                minorThanEquals.setLeftExpression(column);
                minorThanEquals.setRightExpression(valueExpression);

                if (dto.getSpliceType().equals(SpliceTypeEnum.OR.toString())) {
                    return where == null ? minorThanEquals : new OrExpression(where, minorThanEquals);
                } else if (dto.getSpliceType().equals(SpliceTypeEnum.AND.toString())) {
                    return where == null ? minorThanEquals : new AndExpression(where, minorThanEquals);
                } else
                    throw new RuntimeException("错误的拼接类型：" + dto.getSpliceType());

            } else if (dto.getExpression().equals(ExpressionEnum.ISNULL.toString())) {
                Column column = new Column(dto.getColumnName());

                IsNullExpression isNullExpression = new IsNullExpression();
                isNullExpression.setLeftExpression(column);

                if (dto.getSpliceType().equals(SpliceTypeEnum.OR.toString())) {
                    return where == null ? isNullExpression : new OrExpression(where, isNullExpression);
                } else if (dto.getSpliceType().equals(SpliceTypeEnum.AND.toString())) {
                    return where == null ? isNullExpression : new AndExpression(where, isNullExpression);
                } else
                    throw new RuntimeException("错误的拼接类型：" + dto.getSpliceType());

            } else if (dto.getExpression().equals(ExpressionEnum.NOTNULL.toString())) {
                //                Column column = new Column(dto.getColumnName());
                //
                //                IsNotNull isNotNullExpression = new IsNotNull();
                //                isNotNullExpression.setLeftExpression(column);
                //
                //                if (dto.getSpliceType().equals(SpliceTypeEnum.OR.toString())){
                //                    return where == null ? isNullExpression : new OrExpression(where, isNullExpression);
                //                }else if (dto.getSpliceType().equals(SpliceTypeEnum.AND.toString())){
                //                    return where == null ? isNullExpression : new AndExpression(where, isNullExpression);
                //                }else
                //                    throw new RuntimeException("错误的拼接类型：" + dto.getSpliceType());

            } else
                throw new RuntimeException("错误的表达式：" + dto.getExpression());

        } else
            throw new RuntimeException("无效的提供方式：" + dto.getProvideType());
        return where;
    }

}
