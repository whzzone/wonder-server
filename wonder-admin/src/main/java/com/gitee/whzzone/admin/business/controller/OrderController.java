package com.gitee.whzzone.admin.business.controller;

import com.gitee.whzzone.admin.common.base.controller.EntityController;
import com.gitee.whzzone.admin.business.pojo.dto.OrderDto;
import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.admin.business.pojo.query.OrderQuery;
import com.gitee.whzzone.admin.business.service.OrderService;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PostMapping("list")
    public Result<List<OrderDto>> list(@RequestBody OrderQuery query){
        return Result.ok("操作成功", orderService.list(query));
    }

    @ApiOperation("列表-注解在形参")
    @PostMapping("list2")
    public Result<List<OrderDto>> list2(@RequestBody OrderQuery query){
        return Result.ok("操作成功", orderService.list2(query, null));
    }
}
