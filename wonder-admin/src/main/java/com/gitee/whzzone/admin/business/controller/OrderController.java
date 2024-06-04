package com.gitee.whzzone.admin.business.controller;

import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.admin.business.pojo.dto.OrderDTO;
import com.gitee.whzzone.admin.business.pojo.query.OrderQuery;
import com.gitee.whzzone.admin.business.service.OrderService;
import com.gitee.whzzone.web.controller.EntityController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Create by whz at 2023/8/4
 */
@Api(tags = "订单相关")
@RestController
@RequestMapping("order")
public class OrderController extends EntityController<Order, OrderService, OrderDTO, OrderQuery> {

}
