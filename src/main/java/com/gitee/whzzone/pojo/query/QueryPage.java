package com.gitee.whzzone.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author :whz
 * @date : 2022/12/17 19:51
 */

@Data
public class QueryPage {

    @ApiModelProperty(value = "当前页数（从1开始）", required = true)
    private Integer curPage = 1;

    @ApiModelProperty(value = "每页记录数", required = true)
    private Integer pageSize = 10;


}
