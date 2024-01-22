package com.gitee.whzzone.admin.system.pojo.dto;

import com.gitee.whzzone.web.pojo.dto.EntityDTO;
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
@ApiModel(value = "RequestLogDTO对象", description = "请求日志")
public class RequestLogDTO extends EntityDTO {

    @ApiModelProperty("响应码")
    private Integer code;

    @ApiModelProperty("请求人")
    private Integer userId;

    @ApiModelProperty("请求url")
    private String url;

    @ApiModelProperty("接口描述")
    private String desc;

    @ApiModelProperty("请求类型")
    private String type;

    @ApiModelProperty("接口方法")
    private String method;

    @ApiModelProperty("来源IP")
    private String ip;

    @ApiModelProperty("请求参数")
    private String params;

    @ApiModelProperty("请求耗时ms")
    private Long duration;

    @ApiModelProperty("响应数据")
    private String result;

}
