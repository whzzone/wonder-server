package com.gitee.whzzone.admin.system.pojo.query;

import com.gitee.whzzone.web.pojo.query.EntityQuery;
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
