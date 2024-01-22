package com.gitee.whzzone.admin.business.pojo.dto;

import com.gitee.whzzone.annotation.EntityField;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import com.gitee.whzzone.web.validation.groups.InsertGroup;
import com.gitee.whzzone.web.validation.groups.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author Create by whz at 2023/8/4
 */
@Data
public class OrderDTO extends EntityDTO {

    @EntityField
    @NotBlank(message = "收货人不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @EntityField(insert = false)
    @ApiModelProperty("收货人手机号码")
    private String receiverPhone;

    @ApiModelProperty("收货地址")
    private String receiverAddress;

    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("订单状态：0-待付款，1-已取消，2-已付款，3-已完成")
    private Integer orderStatus;
    
}
