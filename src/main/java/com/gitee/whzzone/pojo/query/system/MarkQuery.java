package com.gitee.whzzone.pojo.query.system;

import com.gitee.whzzone.common.base.pojo.quey.EntityQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/15
 */
@Data
public class MarkQuery extends EntityQuery {

    @ApiModelProperty("名称")
    private String name;

}
