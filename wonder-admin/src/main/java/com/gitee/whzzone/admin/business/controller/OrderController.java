package com.gitee.whzzone.admin.business.controller;

import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.admin.business.pojo.dto.OrderDto;
import com.gitee.whzzone.admin.business.pojo.query.OrderQuery;
import com.gitee.whzzone.admin.business.service.OrderService;
import com.gitee.whzzone.web.controller.EntityController;
import com.gitee.whzzone.web.pojo.other.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Create by whz at 2023/8/4
 */
@Api(tags = "订单相关")
@RestController
@RequestMapping("order")
public class OrderController extends EntityController<Order, OrderService, OrderDto, OrderQuery> {

    @Autowired
    private OrderService orderService;

    @ApiOperation("列表")
    @GetMapping("list")
    public Result<List<OrderDto>> list(OrderQuery query){
        return Result.ok("操作成功", orderService.list(query));
    }

}
