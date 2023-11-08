package com.gitee.whzzone.admin.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.whzzone.common.base.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Create by whz at 2023/8/4
 */
@Data
@TableName("ex_order")
public class Order extends BaseEntity {

    private String receiverName;

    private String receiverPhone;

    private String receiverAddress;

    private BigDecimal orderAmount;

    private Integer orderStatus;

}
