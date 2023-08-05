package com.gitee.whzzone.admin.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gitee.whzzone.admin.pojo.dto.system.DataScopeInfo;
import com.gitee.whzzone.common.enums.ExpressionEnum;

/**
 * @author Create by whz at 2023/8/5
 */
public class CommonUtil {

    public static QueryWrapper<?> handleQueryWrapper(QueryWrapper<?> queryWrapper, DataScopeInfo dataScopeInfo) {
        if (dataScopeInfo.getDto().getExpression().equals(ExpressionEnum.IN.toString())){
            queryWrapper.in(dataScopeInfo.getDto().getColumnName(), dataScopeInfo.getIdList());
        }else if (dataScopeInfo.getDto().getExpression().equals(ExpressionEnum.NOT_IN.toString())){
            queryWrapper.notIn(dataScopeInfo.getDto().getColumnName(), dataScopeInfo.getIdList());
        }else {
            throw new RuntimeException("非 IN / NOT_IN 表达式");
        }
        return queryWrapper;
    }

}
