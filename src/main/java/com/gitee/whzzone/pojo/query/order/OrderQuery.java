package com.gitee.whzzone.pojo.query.order;

import com.gitee.whzzone.web.QueryPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Create by whz at 2023/8/4
 */
@Data
public class OrderQuery extends QueryPage {

    @ApiModelProperty("收货人姓名")
    private String receiverName;

    @ApiModelProperty("收货人手机号码")
    private String receiverPhone;

    @ApiModelProperty("收货地址")
    private String receiverAddress;

    @ApiModelProperty("订单状态：0-待付款，1-已取消，2-已付款，3-已完成")
    private Integer orderStatus;

}
