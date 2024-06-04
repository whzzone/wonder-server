package com.gitee.whzzone.admin.system.pojo.dto;

import com.gitee.whzzone.admin.common.mybatis.DataScopeRule;
import lombok.Data;

import java.util.List;

/**
 * @author Create by whz at 2023/7/14
 */
@Data
public class DataScopeInfo {

    private List<DataScopeRule> ruleList;

}
