package com.gitee.whzzone.admin.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.whzzone.admin.pojo.entity.system.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Create by whz at 2023/7/8
 */

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    List<Long> getThisAndChildIds(Long id);

}
