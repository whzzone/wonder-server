package com.gitee.whzzone.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.common.annotation.DataScope;
import com.gitee.whzzone.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.mapper.order.OrderMapper;
import com.gitee.whzzone.pojo.dto.order.OrderDto;
import com.gitee.whzzone.pojo.dto.system.DataScopeInfo;
import com.gitee.whzzone.pojo.entity.order.Order;
import com.gitee.whzzone.pojo.query.order.OrderQuery;
import com.gitee.whzzone.service.order.OrderService;
import com.gitee.whzzone.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Create by whz at 2023/8/4
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @DataScope("order-list")
    @Override
    public List<OrderDto> list(OrderQuery query) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(query.getReceiverName()), Order::getReceiverName, query.getReceiverName());
        queryWrapper.eq(StrUtil.isNotBlank(query.getReceiverPhone()), Order::getReceiverPhone, query.getReceiverPhone());
        queryWrapper.eq(StrUtil.isNotBlank(query.getReceiverAddress()), Order::getReceiverAddress, query.getReceiverAddress());
        queryWrapper.eq(query.getOrderStatus() != null, Order::getOrderStatus, query.getOrderStatus());
        List<Order> list = list(queryWrapper);
        return afterQueryHandler(list);
    }

    @Override
    public List<OrderDto> list2(OrderQuery query, @DataScope("order-list") DataScopeInfo dataScopeInfo) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
//        queryWrapper.in(dataScopeInfo.getDto().getColumnName(), dataScopeInfo.getIdList());
        CommonUtil.handleQueryWrapper(queryWrapper, dataScopeInfo);
        queryWrapper.eq(StrUtil.isNotBlank(query.getReceiverName()), "receiver_name", query.getReceiverName());
        queryWrapper.eq(StrUtil.isNotBlank(query.getReceiverPhone()), "receiver_phone", query.getReceiverPhone());
        queryWrapper.eq(StrUtil.isNotBlank(query.getReceiverAddress()), "receiver_address", query.getReceiverAddress());
        queryWrapper.eq(query.getOrderStatus() != null, "order_status", query.getOrderStatus());
        List<Order> list = list(queryWrapper);
        return afterQueryHandler(list);
    }

    @Override
    public List<Long> limitAmountBetween(BigDecimal begin, BigDecimal end) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(Order::getOrderAmount, begin, end);
        List<Order> list = list(queryWrapper);
        if (CollectionUtil.isEmpty(list))
            return new ArrayList<>();

        return list.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }
}
