package com.gitee.whzzone.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.whzzone.mapper.DataScopeMapper;
import com.gitee.whzzone.pojo.entity.DataScope;
import com.gitee.whzzone.service.DataScopeService;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/7/13
 */

@Service
public class DataScopeServiceImpl extends ServiceImpl<DataScopeMapper, DataScope> implements DataScopeService {

    @Override
    public DataScope findByName(String name) {
        LambdaQueryWrapper<DataScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataScope::getScopeName, name);
        return getOne(queryWrapper);
    }
}
