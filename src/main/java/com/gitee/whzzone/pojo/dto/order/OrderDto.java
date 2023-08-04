package com.gitee.whzzone.pojo.dto.order;

import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Create by whz at 2023/8/4
 */
@Data
public class OrderDto extends EntityDto {

    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人手机号码")
    private String receiverPhone;

    @ApiModelProperty("收货地址")
    private String receiverAddress;

    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("订单状态：0-待付款，1-已取消，2-已付款，3-已完成")
    private Integer orderStatus;
    
}
