package com.example.securitytest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.securitytest.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : whz
 * @date : 2023/5/16 19:33
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
