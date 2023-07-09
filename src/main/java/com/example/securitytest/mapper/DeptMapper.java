package com.example.securitytest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.securitytest.pojo.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Create by whz at 2023/7/8
 */

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    List<Long> getThisAndChildIds(Long id);

}
