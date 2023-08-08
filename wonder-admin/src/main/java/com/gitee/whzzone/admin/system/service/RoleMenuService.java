package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.dto.RoleMenuDto;
import com.gitee.whzzone.admin.system.entity.RoleMenu;
import com.gitee.whzzone.admin.system.pojo.query.RoleMenuQuery;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/23 10:02
 */
public interface RoleMenuService extends EntityService<RoleMenu, RoleMenuDto, RoleMenuQuery> {

    void addRelation(Long roleId, List<Long> menuIds);

    void removeRelation(Long roleId);

}
