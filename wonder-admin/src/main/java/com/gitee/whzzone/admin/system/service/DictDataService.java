package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.web.service.EntityService;
import com.gitee.whzzone.admin.system.entity.DictData;
import com.gitee.whzzone.admin.system.pojo.other.DictData.DictDataDTO;
import com.gitee.whzzone.admin.system.pojo.other.DictData.DictDataQuery;

import java.util.List;

/**
* 系统字典数据 服务类
* @author Create by generator at 2023/8/8
*/
public interface DictDataService extends EntityService<DictData, DictDataDTO, DictDataQuery> {

    boolean existSameDictValue(Integer id, Integer dictId, String dictValue);

    List<DictDataDTO> findByDictCode(String dictCode);
}
