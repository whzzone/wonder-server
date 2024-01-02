package com.gitee.whzzone.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.system.entity.Dict;
import com.gitee.whzzone.admin.system.entity.DictData;
import com.gitee.whzzone.admin.system.mapper.DictDataMapper;
import com.gitee.whzzone.admin.system.pojo.other.DictData.DictDataDto;
import com.gitee.whzzone.admin.system.pojo.other.DictData.DictDataQuery;
import com.gitee.whzzone.admin.system.service.DictDataService;
import com.gitee.whzzone.admin.system.service.DictService;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* 系统字典数据 服务实现类
* @author Create by generator at 2023/8/8
*/
@Service
public class DictDataServiceImpl extends EntityServiceImpl<DictDataMapper, DictData, DictDataDto, DictDataQuery> implements DictDataService {

    @Autowired
    private DictService dictService;

    @Override
    public DictDataDto beforeSaveOrUpdateHandler(DictDataDto dto) {
        if (!dictService.isExist(dto.getDictId())) {
            throw new RuntimeException("不存在字典：" + dto.getDictId());
        }

        if (existSameDictValue(dto.getId(), dto.getDictId(), dto.getDictValue())){
            throw new RuntimeException("该字典中存在相同的值：" + dto.getDictValue());
        }

        return dto;
    }

    @Override
    public boolean existSameDictValue(Integer id, Integer dictId, String dictValue) {
        LambdaQueryWrapper<DictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DictData::getDictValue, dictValue);
        queryWrapper.eq(DictData::getDictId, dictId);
        queryWrapper.ne(id != null, DictData::getId, id);
        return count(queryWrapper) > 0;
    }

    @Override
    public List<DictDataDto> findByDictCode(String dictCode) {
        Dict dict = dictService.findByDictCode(dictCode);
        if (dict == null) {
            return null;
        }

        List<DictData> dictDataList = findByDictId(dict.getId());

        return BeanUtil.copyToList(dictDataList, DictDataDto.class);
    }

    public List<DictData> findByDictId(Integer dictId){
        LambdaQueryWrapper<DictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DictData::getDictId, dictId);
        return list(queryWrapper);
    }
}
