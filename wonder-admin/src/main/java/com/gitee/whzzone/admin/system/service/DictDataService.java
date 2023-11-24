package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.admin.system.entity.DictData;
import com.gitee.whzzone.admin.system.pojo.other.DictData.DictDataDto;
import com.gitee.whzzone.admin.system.pojo.other.DictData.DictDataQuery;

import java.util.List;

/**
* 系统字典数据 服务类
* @author Create by generator at 2023/8/8
*/
public interface DictDataService extends EntityService<DictData, DictDataDto, DictDataQuery> {

    boolean existSameDictValue(Long id, Long dictId, String dictValue);

    List<DictDataDto> findByDictCode(String dictCode);
}