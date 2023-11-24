package com.gitee.whzzone.admin.system.pojo.other.Dict;

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
* 系统字典
* @author Create by generator at 2023/8/8
*/
@Getter
@Setter
@ToString
@SelectColumn({"id", "create_time", "create_by", "update_time", "update_by", "deleted", "dict_name", "dict_code", "dict_type", "sort", "remark"})
@ApiModel(value = "DictQuery对象", description = "系统字典")
public class DictQuery extends EntityQuery {

    @Query(expression = ExpressionEnum.LIKE)
    @ApiModelProperty("字典名称")
    private String dictName;

    @Query
    @ApiModelProperty("字典编码（唯一）")
    private String dictCode;

    @Query
    @ApiModelProperty("字典类型，0-列表，1-树")
    private Integer dictType;

    @Query
    @ApiModelProperty("排序")
    private Integer sort;

    @Query
    @ApiModelProperty("备注")
    private String remark;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("开始日期")
    private Date beginDate;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("结束日期")
    private Date endDate;

    @ApiModelProperty("排序")
    private List<Sort> sorts = Arrays.asList(new Sort("sort", true));

}