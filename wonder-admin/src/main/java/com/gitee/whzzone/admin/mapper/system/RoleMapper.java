package com.gitee.whzzone.admin.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.whzzone.admin.common.annotation.DataScope;
import com.gitee.whzzone.admin.pojo.entity.system.Role;
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
