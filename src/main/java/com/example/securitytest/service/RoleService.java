package com.example.securitytest.service;

import com.example.securitytest.pojo.dto.RoleDto;
import com.example.securitytest.pojo.entity.Role;
import com.example.securitytest.pojo.query.RoleQuery;
import com.example.securitytest.pojo.vo.IdAndNameVo;
import com.example.securitytest.pojo.vo.PageData;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/22 15:38
 */
public interface RoleService extends IEntityService<Role, RoleDto> {
//    @Override
//    default Class<Role> getEntityClass() {
//        return Role.class;
//    }

    @Override
    default Class<RoleDto> getDtoClass() {
        return RoleDto.class;
    }

    List<IdAndNameVo> getAll();

    PageData<RoleDto> findPage(RoleQuery query);

    void updateRoleMenu(RoleDto dto);
}
