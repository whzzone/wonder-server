package com.gitee.whzzone.admin.business.service;

import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.admin.business.pojo.dto.OrderDTO;
import com.gitee.whzzone.admin.business.pojo.query.OrderQuery;
import com.gitee.whzzone.web.service.EntityService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Create by whz at 2023/8/4
 */
public interface OrderService extends EntityService<Order, OrderDTO, OrderQuery> {

    List<OrderDTO> list(OrderQuery query);

    List<Integer> limitAmountBetween(BigDecimal begin, BigDecimal end);

}
