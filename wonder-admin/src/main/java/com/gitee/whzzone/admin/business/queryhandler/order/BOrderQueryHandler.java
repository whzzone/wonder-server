package com.gitee.whzzone.admin.business.queryhandler.order;

import cn.hutool.core.bean.BeanUtil;
import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.admin.business.pojo.dto.OrderDTO;
import com.gitee.whzzone.web.queryhandler.BaseQueryHandlerImpl;

/**
 * @author Create by whz at 2023/11/8
 */
//@Service
public class BOrderQueryHandler extends BaseQueryHandlerImpl<Order, OrderDTO> implements OrderQueryHandler{

    @Override
    public OrderDTO process(Order order) {
        OrderDTO orderDTO = BeanUtil.copyProperties(order, OrderDTO.class);
        orderDTO.setReceiverName("BBBBBBBBBB");
        return orderDTO;
    }

}
