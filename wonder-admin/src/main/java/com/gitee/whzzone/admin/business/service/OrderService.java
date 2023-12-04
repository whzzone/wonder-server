package com.gitee.whzzone.admin.business.service;

import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.admin.business.pojo.dto.OrderDto;
import com.gitee.whzzone.admin.business.pojo.query.OrderQuery;
import com.gitee.whzzone.common.base.service.EntityService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Create by whz at 2023/8/4
 */
public interface OrderService extends EntityService<Order, OrderDto, OrderQuery> {

    List<OrderDto> list(OrderQuery query);

    List<Long> limitAmountBetween(BigDecimal begin, BigDecimal end);

}
