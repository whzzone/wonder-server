package com.example.securitytest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.securitytest.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

;

/**
 * @author : whz
 * @date : 2022/11/30 10:16
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findByUserId(Integer userId);
}
