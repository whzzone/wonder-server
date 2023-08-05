package com.gitee.whzzone.admin.service.system;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.pojo.dto.system.RoleMenuDto;
import com.gitee.whzzone.admin.pojo.entity.system.RoleMenu;
import com.gitee.whzzone.admin.pojo.query.system.RoleMenuQuery;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/23 10:02
 */
public interface RoleMenuService extends EntityService<RoleMenu, RoleMenuDto, RoleMenuQuery> {

    void addRelation(Long roleId, List<Long> menuIds);

    void removeRelation(Long roleId);

}
