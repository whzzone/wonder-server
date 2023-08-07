package com.gitee.whzzone.admin.pojo.query.system;

import com.gitee.whzzone.admin.common.base.pojo.quey.EntityQuery;
import com.gitee.whzzone.common.annotation.Query;
import com.gitee.whzzone.common.annotation.QueryOrder;
import com.gitee.whzzone.common.annotation.QuerySort;
import com.gitee.whzzone.common.enums.ExpressionEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
* 请求日志
* @author Create by generator at 2023/08/07
*/
@Getter
@Setter
@ToString
@ApiModel(value = "RequestLogQuery对象", description = "请求日志")
public class RequestLogQuery extends EntityQuery {

    @Query
    @ApiModelProperty("响应码")
    private Integer code;

    @Query
    @ApiModelProperty("请求人")
    private Long userId;

    @Query
    @ApiModelProperty("请求url")
    private String url;

    @Query
    @ApiModelProperty("接口描述")
    private String desc;

    @Query
    @ApiModelProperty("请求类型")
    private String type;

    @Query
    @ApiModelProperty("接口方法")
    private String method;

    @Query
    @ApiModelProperty("来源IP")
    private String ip;

    @Query
    @ApiModelProperty("请求参数")
    private String params;

    @Query
    @ApiModelProperty("请求耗时ms")
    private Long duration;

    @Query
    @ApiModelProperty("响应数据")
    private String result;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN, left = true)
    @ApiModelProperty("开始时间")
    private Date startTime;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN, left = false)
    @ApiModelProperty("结束时间")
    private Date endTime;

    @QuerySort
    @ApiModelProperty("排序字段")
    private String sortColumn;

    @QueryOrder
    @ApiModelProperty("排序方式-asc/desc")
    private String sortOrder;

}
