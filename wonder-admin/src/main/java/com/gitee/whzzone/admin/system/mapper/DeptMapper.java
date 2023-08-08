package com.gitee.whzzone.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.whzzone.admin.system.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Create by whz at 2023/7/8
 */

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

    List<Long> getThisAndChildIds(Long id);

}
