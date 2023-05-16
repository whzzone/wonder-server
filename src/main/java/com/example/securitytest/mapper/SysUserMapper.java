package com.example.securitytest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.securitytest.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : whz
 * @date : 2023/5/16 19:33
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
