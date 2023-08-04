package com.gitee.whzzone.pojo.query.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/8
 */

@Data
public class DeptQuery {

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("父级id")
    private Long parentId;

    @ApiModelProperty("是否启用")
    private Boolean enabled;

}
