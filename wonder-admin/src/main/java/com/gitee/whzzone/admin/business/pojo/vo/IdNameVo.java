package com.gitee.whzzone.admin.business.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Create by whz at 2024/3/19
 */
@Data
@NoArgsConstructor
public class IdNameVo {

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("名称")
    private String name;

    /**
     * 构造函数
     * @param id 主键
     * @param delimiter 分隔符
     * @param elements 要拼接元素
     */
    public IdNameVo(Integer id, String delimiter, String... elements) {
        this.id = id;
        this.name = String.join(delimiter, elements);
    }

    public IdNameVo(Integer id, String element) {
        this.id = id;
        this.name = element;
    }

}
