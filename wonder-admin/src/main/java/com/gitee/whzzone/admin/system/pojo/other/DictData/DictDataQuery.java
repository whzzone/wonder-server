package com.gitee.whzzone.admin.system.pojo.other.DictData;

import com.gitee.whzzone.admin.common.base.pojo.quey.EntityQuery;
import com.gitee.whzzone.common.annotation.Query;
import com.gitee.whzzone.common.annotation.QueryOrder;
import com.gitee.whzzone.common.annotation.QuerySort;
import com.gitee.whzzone.common.annotation.SelectColumn;
import com.gitee.whzzone.common.enums.ExpressionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
* 系统字典数据
* @author Create by generator at 2023/8/8
*/
@Getter
@Setter
@ToString
@SelectColumn({"id", "create_time", "create_by", "update_time", "update_by", "deleted", "dict_id", "parent_id", "dict_label", "dict_value", "remark", "sort"})
@ApiModel(value = "DictDataQuery对象", description = "系统字典数据")
public class DictDataQuery extends EntityQuery {

    @Query
    @ApiModelProperty("字典id")
    private Long dictId;

    @Query
    @ApiModelProperty("父级id")
    private Long parentId;

    @Query
    @ApiModelProperty("字典标签")
    private String dictLabel;

    @Query
    @ApiModelProperty("字典值")
    private String dictValue;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN, left = true)
    @ApiModelProperty("开始时间")
    private Date startTime;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN, left = false)
    @ApiModelProperty("结束时间")
    private Date endTime;

    @QuerySort("sort")
    @ApiModelProperty("排序字段")
    private String sortColumn;

    @QueryOrder("asc")
    @ApiModelProperty("排序方式，asc/desc")
    private String sortOrder;

}
