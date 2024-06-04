package com.gitee.whzzone.admin.business.pojo.dto;

import com.gitee.whzzone.annotation.EntityField;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import com.gitee.whzzone.web.validation.groups.AddGroup;
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

    @EntityField(updateRequired = true)
    @NotBlank(message = "收货人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "收货人姓名", required = true)
    private String receiverName;

    @EntityField(addAble = true, updateAble = false)
    @ApiModelProperty("收货人手机号码")
    private String receiverPhone;

    @ApiModelProperty("收货地址")
    private String receiverAddress;

    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("订单状态：0-待付款，1-已取消，2-已付款，3-已完成")
    private Integer orderStatus;

    @ApiModelProperty("部门id")
    private Integer deptId;
    
}
