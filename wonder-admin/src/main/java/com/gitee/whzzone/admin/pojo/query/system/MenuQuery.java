package com.gitee.whzzone.admin.pojo.query.system;

import com.gitee.whzzone.admin.common.base.pojo.quey.EntityQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :whz
 * @date : 2022/12/18 14:32
 */
@Data
public class MenuQuery extends EntityQuery {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("父id")
    private Long parentId;

    @ApiModelProperty("菜单类型")
    private Integer menuType;


}
