package com.gitee.whzzone.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.whzzone.web.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 请求日志
 * @author Create by generator at 2023/08/07
 */
@Getter
@Setter
@ToString
@TableName("sys_request_log")
@ApiModel(value = "RequestLog对象", description = "请求日志")
public class RequestLog extends BaseEntity {

    @ApiModelProperty("请求人")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty("请求uri")
    @TableField("uri")
    private String uri;

    @ApiModelProperty("接口描述")
    @TableField("`desc`")
    private String desc;

    @ApiModelProperty("请求方法")
    @TableField("method")
    private String method;

    @ApiModelProperty("来源IP")
    @TableField("ip")
    private String ip;

    @ApiModelProperty("query参数")
    @TableField("query_params")
    private String queryParams;

    @ApiModelProperty("body参数")
    @TableField("body_params")
    private String bodyParams;

    @ApiModelProperty("请求耗时ms")
    @TableField("duration")
    private Long duration;

    @ApiModelProperty("响应数据")
    @TableField("result")
    private String result;

}
