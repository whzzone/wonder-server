package com.gitee.whzzone.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.whzzone.admin.system.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : whz
 * @date : 2022/11/30 10:16
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
//    void insertBatch(@Param("list") List<UserRole> userRoleList);
}
