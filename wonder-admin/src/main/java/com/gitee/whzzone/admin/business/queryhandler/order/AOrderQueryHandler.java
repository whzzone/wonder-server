package com.gitee.whzzone.admin.business.queryhandler.order;

import cn.hutool.core.bean.BeanUtil;
import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.admin.business.pojo.dto.OrderDTO;
import com.gitee.whzzone.admin.system.service.UserService;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.web.queryhandler.BaseQueryHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/11/8
 */
@Service
public class AOrderQueryHandler extends BaseQueryHandlerImpl<Order, OrderDTO> implements OrderQueryHandler{

    @Autowired
    private UserService userService;

    @Override
    public OrderDTO process(Order order) {
        OrderDTO orderDTO = BeanUtil.copyProperties(order, OrderDTO.class);
        orderDTO.setReceiverName(userService.getById(SecurityUtil.getLoginUser().getId()).getNickname());
        return orderDTO;
    }

}
