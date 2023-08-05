package com.gitee.whzzone.admin.pojo.query.system;

import com.gitee.whzzone.admin.common.base.pojo.quey.EntityQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/8
 */

@Data
public class DeptQuery extends EntityQuery {

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("父级id")
    private Long parentId;

    @ApiModelProperty("是否启用")
    private Boolean enabled;

}
