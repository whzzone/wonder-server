package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.web.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.dto.RoleMenuDTO;
import com.gitee.whzzone.admin.system.entity.RoleMenu;
import com.gitee.whzzone.admin.system.pojo.query.RoleMenuQuery;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/23 10:02
 */
public interface RoleMenuService extends EntityService<RoleMenu, RoleMenuDTO, RoleMenuQuery> {

    void addRelation(Integer roleId, List<Integer> menuIds);

    void removeRelation(Integer roleId);

}
