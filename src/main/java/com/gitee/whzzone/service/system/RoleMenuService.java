package com.gitee.whzzone.service.system;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.pojo.dto.system.RoleMenuDto;
import com.gitee.whzzone.pojo.entity.system.RoleMenu;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/5/23 10:02
 */
public interface RoleMenuService extends EntityService<RoleMenu, RoleMenuDto> {

    void addRelation(Long roleId, List<Long> menuIds);

    void removeRelation(Long roleId);

}
