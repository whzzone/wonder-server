package com.gitee.whzzone.service;

import com.gitee.whzzone.pojo.dto.RoleMenuDto;
import com.gitee.whzzone.pojo.entity.RoleMenu;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/23 10:02
 */
public interface RoleMenuService extends IEntityService<RoleMenu, RoleMenuDto> {

    void addRelation(Long roleId, List<Long> menuIds);

    void removeRelation(Long roleId);

}
