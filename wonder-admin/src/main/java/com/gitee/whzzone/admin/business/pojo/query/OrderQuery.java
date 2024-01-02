package com.gitee.whzzone.admin.business.pojo.query;

import com.gitee.whzzone.annotation.Query;
import com.gitee.whzzone.common.enums.ExpressionEnum;
import com.gitee.whzzone.web.pojo.query.EntityQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

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

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("开始日期")
    private Date startDate;

    @Query(column = "create_time", expression = ExpressionEnum.BETWEEN)
    @ApiModelProperty("结束日期")
    private Date endDate;

}
