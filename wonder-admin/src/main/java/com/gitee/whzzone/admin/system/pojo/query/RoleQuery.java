package com.gitee.whzzone.admin.system.pojo.query;

import com.gitee.whzzone.web.pojo.query.EntityQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :whz
 * @date : 2022/12/18 14:32
 */
@Data
public class RoleQuery extends EntityQuery {

    @ApiModelProperty("名称")
    private String name;


}
