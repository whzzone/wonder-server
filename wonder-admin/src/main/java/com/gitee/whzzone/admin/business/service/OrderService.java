package com.gitee.whzzone.admin.business.service;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.business.pojo.dto.OrderDto;
import com.gitee.whzzone.admin.system.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.admin.business.pojo.query.OrderQuery;

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
