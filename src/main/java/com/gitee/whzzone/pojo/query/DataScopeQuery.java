package com.gitee.whzzone.pojo.query;

import com.gitee.whzzone.web.QueryPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/15
 */
@Data
public class DataScopeQuery extends QueryPage {

    @ApiModelProperty("名称")
    private String name;

}
