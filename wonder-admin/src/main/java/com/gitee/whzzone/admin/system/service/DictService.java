package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.system.entity.Dict;
import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.other.Dict.DictQuery;
import com.gitee.whzzone.admin.system.pojo.other.Dict.DictDto;

/**
* 系统字典 服务类
* @author Create by generator at 2023/8/8
*/
public interface DictService extends EntityService<Dict, DictDto, DictQuery> {

    boolean existSameDictCode(Long id, String dictCode);

    Dict findByDictCode(String dictCode);
}
