package com.gitee.whzzone.admin.system.pojo.other.DictData;

import com.gitee.whzzone.common.annotation.Query;
import com.gitee.whzzone.common.annotation.SelectColumn;
import com.gitee.whzzone.common.base.pojo.query.EntityQuery;
import com.gitee.whzzone.common.base.pojo.sort.Sort;
import com.gitee.whzzone.common.enums.ExpressionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("开始时间")
    private Date startTime;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("排序")
    private List<Sort> sorts = Arrays.asList(new Sort("sort", true));

}
