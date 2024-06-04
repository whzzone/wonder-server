package com.gitee.whzzone.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.whzzone.admin.system.entity.Role;
import com.gitee.whzzone.annotation.DataScope;
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

    @DataScope("sn4")
    List<Role> selectAllTest();

}
