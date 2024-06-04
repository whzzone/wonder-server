package com.gitee.whzzone.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.system.entity.Dict;
import com.gitee.whzzone.admin.system.mapper.DictMapper;
import com.gitee.whzzone.admin.system.pojo.other.Dict.DictDTO;
import com.gitee.whzzone.admin.system.pojo.other.Dict.DictQuery;
import com.gitee.whzzone.admin.system.service.DictService;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import org.springframework.stereotype.Service;

/**
* 系统字典 服务实现类
* @author Create by generator at 2023/8/8
*/
@Service
public class DictServiceImpl extends EntityServiceImpl<DictMapper, Dict, DictDTO, DictQuery> implements DictService {

    @Override
    public DictDTO beforeAddOrUpdateHandler(DictDTO dto) {
        if (existSameDictCode(dto.getId(), dto.getDictCode())){
            throw new RuntimeException("存在相同的字典编码");
        }
        return dto;
    }

    @Override
    public boolean existSameDictCode(Integer id, String dictCode) {
        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dict::getDictCode, dictCode);
        queryWrapper.ne(id != null, Dict::getId, id);
        return count(queryWrapper) > 0;
    }

    @Override
    public Dict findByDictCode(String dictCode) {
        if (dictCode.isEmpty()){
            throw new RuntimeException("dictCode 不能为空");
        }

        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dict::getDictCode, dictCode);
        return getOne(queryWrapper);
    }
}
