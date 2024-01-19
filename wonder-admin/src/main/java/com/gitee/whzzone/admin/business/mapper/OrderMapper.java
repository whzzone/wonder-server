package com.gitee.whzzone.admin.business.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gitee.whzzone.admin.business.entity.Order;
import com.gitee.whzzone.annotation.DataScope;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Create by whz at 2023/8/4
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @DataScope("order-list")
    @Override
    List<Order> selectList(@Param(Constants.WRAPPER) Wrapper<Order> queryWrapper);

    @DataScope("order-list")
    List<Order> listTest( @Param("id") Integer id);
}
