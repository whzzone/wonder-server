package com.gitee.whzzone.admin.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.admin.business.mapper.OrderMapper;
import com.gitee.whzzone.admin.business.pojo.dto.OrderDTO;
import com.gitee.whzzone.admin.business.pojo.query.OrderQuery;
import com.gitee.whzzone.admin.business.service.OrderService;
import com.gitee.whzzone.web.entity.BaseEntity;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Create by whz at 2023/8/4
 */
@Service
public class OrderServiceImpl extends EntityServiceImpl<OrderMapper, Order, OrderDTO, OrderQuery> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /*@Override
    public List<OrderDTO> list(OrderQuery query) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(query.getReceiverName()), Order::getReceiverName, query.getReceiverName());
        queryWrapper.eq(StrUtil.isNotBlank(query.getReceiverPhone()), Order::getReceiverPhone, query.getReceiverPhone());
        queryWrapper.eq(StrUtil.isNotBlank(query.getReceiverAddress()), Order::getReceiverAddress, query.getReceiverAddress());
        queryWrapper.eq(query.getOrderStatus() != null, Order::getOrderStatus, query.getOrderStatus());
        // AOrderQueryHandler 已注入容器
//        return afterQueryHandler(list(queryWrapper), AOrderQueryHandler.class);

        // BOrderQueryHandler未注入容器
        return afterQueryHandler(list(queryWrapper), new BOrderQueryHandler());
//        return afterQueryHandler(orderMapper.listTest(1), new BOrderQueryHandler());
    }*/

    @Override
    public List<Integer> limitAmountBetween(BigDecimal begin, BigDecimal end) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(Order::getOrderAmount, begin, end);
        queryWrapper.select(BaseEntity::getId);
        List<Order> list = list(queryWrapper);
        if (CollectionUtil.isEmpty(list))
            return new ArrayList<>();

        return list.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }
}
