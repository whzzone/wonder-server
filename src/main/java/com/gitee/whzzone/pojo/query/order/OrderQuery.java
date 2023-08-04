package com.gitee.whzzone.pojo.query.order;

import com.gitee.whzzone.common.annotation.Query;
import com.gitee.whzzone.common.base.pojo.quey.EntityQuery;
import com.gitee.whzzone.common.enums.ExpressionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Create by whz at 2023/8/4
 */
@Data
public class OrderQuery extends EntityQuery {

    @Query(expression = ExpressionEnum.LIKE)
    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @Query(column = "receiver_phone", expression = ExpressionEnum.LIKE)
    @ApiModelProperty("收货人手机号码")
    private String receiverPhone;

    @Query(expression = ExpressionEnum.LIKE)
    @ApiModelProperty("收货地址")
    private String receiverAddress;

    @Query
    @ApiModelProperty("订单状态：0-待付款，1-已取消，2-已付款，3-已完成")
    private Integer orderStatus;

}
