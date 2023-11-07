package com.gitee.whzzone.admin.business.pojo.dto;

import com.gitee.whzzone.common.annotation.SaveField;
import com.gitee.whzzone.common.annotation.UpdateField;
import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.group.CreateGroup;
import com.gitee.whzzone.common.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author Create by whz at 2023/8/4
 */
@Data
public class OrderDto extends EntityDto {

    @SaveField(required = true)
    @UpdateField(required = true)
    @NotBlank(message = "收货人不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @UpdateField
    @ApiModelProperty("收货人手机号码")
    private String receiverPhone;

    @UpdateField
    @ApiModelProperty("收货地址")
    private String receiverAddress;

//    @SaveField
//    @UpdateField
    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("订单状态：0-待付款，1-已取消，2-已付款，3-已完成")
    private Integer orderStatus;
    
}
