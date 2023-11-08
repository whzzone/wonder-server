package com.gitee.whzzone.admin.business.queryhandler.order;

import cn.hutool.core.bean.BeanUtil;
import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.admin.business.pojo.dto.OrderDto;
import com.gitee.whzzone.admin.system.service.UserService;
import com.gitee.whzzone.admin.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/11/8
 */
@Service
public class AOrderQueryHandler implements OrderQueryHandler{

    @Autowired
    private UserService userService;

    @Override
    public OrderDto apply(Order order) {
        OrderDto orderDto = BeanUtil.copyProperties(order, OrderDto.class);
        orderDto.setReceiverName(userService.getById(SecurityUtil.getLoginUser().getId()).getNickname());
        return orderDto;
    }

}
