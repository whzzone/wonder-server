package com.example.securitytest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.securitytest.pojo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : whz
 * @date : 2022/11/30 10:16
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> findPermitByUserId(Long userId);

    List<Menu> getRoutes(@Param("userId") Long userId, @Param("code") Integer code);

    List<Menu> findByUserId(Long userId);
}
