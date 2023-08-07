package com.gitee.whzzone.admin.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.whzzone.admin.common.base.pojo.entity.BaseEntity;
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
public class RequestLog extends BaseEntity<RequestLog> {

    @ApiModelProperty("响应码")
    @TableField("`code`")
    private Integer code;

    @ApiModelProperty("请求人")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("请求url")
    @TableField("url")
    private String url;

    @ApiModelProperty("接口描述")
    @TableField("`desc`")
    private String desc;

    @ApiModelProperty("请求类型")
    @TableField("`type`")
    private String type;

    @ApiModelProperty("接口方法")
    @TableField("method")
    private String method;

    @ApiModelProperty("来源IP")
    @TableField("ip")
    private String ip;

    @ApiModelProperty("请求参数")
    @TableField("params")
    private String params;

    @ApiModelProperty("请求耗时ms")
    @TableField("duration")
    private Long duration;

    @ApiModelProperty("响应数据")
    @TableField("result")
    private String result;

}
