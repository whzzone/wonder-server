package com.gitee.whzzone.admin.service.order;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.pojo.dto.order.OrderDto;
import com.gitee.whzzone.admin.pojo.dto.system.DataScopeInfo;
import com.gitee.whzzone.admin.pojo.entity.order.Order;
import com.gitee.whzzone.admin.pojo.query.order.OrderQuery;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Create by whz at 2023/8/4
 */
public interface OrderService extends EntityService<Order, OrderDto, OrderQuery> {

    List<OrderDto> list(OrderQuery query);

    List<OrderDto> list2(OrderQuery query, DataScopeInfo dataScopeInfo);

    List<Long> limitAmountBetween(BigDecimal begin, BigDecimal end);

}
