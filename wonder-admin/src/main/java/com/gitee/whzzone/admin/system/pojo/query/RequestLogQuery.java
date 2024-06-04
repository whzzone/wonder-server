package com.gitee.whzzone.admin.system.pojo.query;

import com.gitee.whzzone.annotation.Query;
import com.gitee.whzzone.common.enums.ExpressionEnum;
import com.gitee.whzzone.web.pojo.query.EntityQuery;
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

    @Query(expression = ExpressionEnum.LIKE)
    @ApiModelProperty("请求url")
    private String url;

    @Query(expression = ExpressionEnum.LIKE)
    @ApiModelProperty("接口描述")
    private String desc;

    @Query
    @ApiModelProperty("请求类型")
    private String type;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("开始时间")
    private Date startTime;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("结束时间")
    private Date endTime;

}
