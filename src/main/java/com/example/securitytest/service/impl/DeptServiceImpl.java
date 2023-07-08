package com.example.securitytest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitytest.mapper.DeptMapper;
import com.example.securitytest.pojo.dto.DeptDto;
import com.example.securitytest.pojo.entity.Dept;
import com.example.securitytest.pojo.query.DeptQuery;
import com.example.securitytest.pojo.vo.PageData;
import com.example.securitytest.service.DeptService;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/7/8
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {


    @Override
    public PageData<DeptDto> page(DeptQuery query) {
        return null;
    }
}
