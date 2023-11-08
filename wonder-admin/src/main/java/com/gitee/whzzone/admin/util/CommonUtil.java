package com.gitee.whzzone.admin.util;

/**
 * @author Create by whz at 2023/8/5
 */
public class CommonUtil {

/*    public static QueryWrapper<?> handleQueryWrapper(QueryWrapper<?> queryWrapper, DataScopeInfo dataScopeInfo) {
        for (RuleDto dto : dataScopeInfo.getDtoList()) {
            if (dto.getExpression().equals(ExpressionEnum.IN.toString())){
                queryWrapper.in(dto.getColumnName(), dataScopeInfo.getIdList());
            }else if (dto.getExpression().equals(ExpressionEnum.NOT_IN.toString())){
                queryWrapper.notIn(dto.getColumnName(), dataScopeInfo.getIdList());
            }else {
                throw new RuntimeException("非 IN / NOT_IN 表达式");
            }
        }

        return queryWrapper;
    }*/

}
