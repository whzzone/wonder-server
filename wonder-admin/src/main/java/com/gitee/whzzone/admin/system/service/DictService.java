package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.admin.system.entity.Dict;
import com.gitee.whzzone.web.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.other.Dict.DictQuery;
import com.gitee.whzzone.admin.system.pojo.other.Dict.DictDTO;

/**
* 系统字典 服务类
* @author Create by generator at 2023/8/8
*/
public interface DictService extends EntityService<Dict, DictDTO, DictQuery> {

    boolean existSameDictCode(Integer id, String dictCode);

    Dict findByDictCode(String dictCode);
}
