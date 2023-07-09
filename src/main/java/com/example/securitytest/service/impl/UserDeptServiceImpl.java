package com.example.securitytest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitytest.mapper.UserDeptMapper;
import com.example.securitytest.pojo.entity.UserDept;
import com.example.securitytest.service.UserDeptService;
import org.springframework.stereotype.Service;

/**
 * @author Create by whz at 2023/7/9
 */
@Service
public class UserDeptServiceImpl extends ServiceImpl<UserDeptMapper, UserDept> implements UserDeptService {

}
