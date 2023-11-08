package com.gitee.whzzone.admin.business.queryhandler.order;

import cn.hutool.core.bean.BeanUtil;
import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.admin.business.pojo.dto.OrderDto;

/**
 * @author Create by whz at 2023/11/8
 */
//@Service
public class BOrderQueryHandler implements OrderQueryHandler{

    @Override
    public OrderDto apply(Order order) {
        OrderDto orderDto = BeanUtil.copyProperties(order, OrderDto.class);
        orderDto.setReceiverName("BBBBBBBBBB");
        return orderDto;
    }

}
