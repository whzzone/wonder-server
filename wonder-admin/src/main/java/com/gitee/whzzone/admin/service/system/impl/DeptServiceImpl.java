package com.gitee.whzzone.admin.service.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gitee.whzzone.admin.common.base.pojo.entity.BaseEntity;
import com.gitee.whzzone.admin.common.base.service.impl.EntityServiceImpl;
import com.gitee.whzzone.admin.mapper.system.DeptMapper;
import com.gitee.whzzone.admin.pojo.dto.system.DeptDto;
import com.gitee.whzzone.admin.pojo.entity.system.Dept;
import com.gitee.whzzone.admin.pojo.query.system.DeptQuery;
import com.gitee.whzzone.admin.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Create by whz at 2023/7/8
 */

@Service
public class DeptServiceImpl extends EntityServiceImpl<DeptMapper, Dept, DeptDto, DeptQuery> implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<DeptDto> list(DeptQuery query) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(query.getName()), Dept::getName, query.getName());
        queryWrapper.eq(query.getParentId() != null, Dept::getParentId, query.getParentId());
        queryWrapper.orderByAsc(Dept::getSort);
        return afterQueryHandler(list(queryWrapper));
    }

    @Transactional
    @Override
    public Dept save(DeptDto dto) {
        if (dto.getParentId() == null) {
            dto.setParentId(0L);
        } else if (!isExist(dto.getParentId()))
            throw new RuntimeException("父级不存在");

        if (dto.getEnabled() == null) {
            dto.setEnabled(true);
        }
        if (dto.getSort() == null) {
            dto.setSort(99);
        }
        return super.save(dto);
    }

    @Transactional
    @Override
    public boolean updateById(DeptDto dto) {
        if (dto.getParentId() != null && !dto.getParentId().equals(0L) && !isExist(dto.getParentId())) {
            throw new RuntimeException("父级不存在");
        }
        return super.updateById(dto);
    }

    @Override
    public boolean hasChildren(Long id) {
        return count(new LambdaQueryWrapper<Dept>().eq(Dept::getParentId, id)) > 0;
    }

    @Override
    public DeptDto afterQueryHandler(Dept entity) {
        DeptDto dto = super.afterQueryHandler(entity);

        dto.setHasChildren(hasChildren(dto.getId()));

        if (!dto.getParentId().equals(0L)) {
            Dept parent = getById(dto.getParentId());
            if (parent != null) {
                dto.setParentName(parent.getName());
            }
        }

        return dto;
    }

    @Override
    public List<DeptDto> tree(DeptQuery query) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(query.getParentId() != null, Dept::getParentId, query.getParentId());
        queryWrapper.eq(query.getEnabled() != null, Dept::getEnabled, query.getEnabled());
        queryWrapper.like(StrUtil.isNotBlank(query.getName()), Dept::getName, query.getName());

        List<Dept> deptList = list(queryWrapper);

        List<DeptDto> dtoList = BeanUtil.copyToList(deptList, DeptDto.class);

        if (CollectionUtil.isNotEmpty(dtoList) && dtoList.size() == 1) {
            DeptDto dto = dtoList.get(0);
            dto.setChildren(new ArrayList<>());
            dto.setHasChildren(false);
            return dtoList;
        }

        //获取父节点
        return dtoList.stream().filter(m -> m.getParentId() == 0).map(
                (m) -> {
                    m.setChildren(getChildrenList(m, dtoList));
                    m.setHasChildren(CollectionUtil.isNotEmpty(m.getChildren()));
                    return m;
                }
        ).collect(Collectors.toList());
    }

    private List<DeptDto> getChildrenList(DeptDto tree, List<DeptDto> list) {
        List<DeptDto> children = list.stream().filter(item -> Objects.equals(item.getParentId(), tree.getId())).map(
                (item) -> {
                    item.setChildren(getChildrenList(item, list));
                    item.setHasChildren(CollectionUtil.isNotEmpty(item.getChildren()));
                    return item;
                }
        ).collect(Collectors.toList());
        return children;
    }

    @Override
    public void enabledSwitch(Long id) {
        Dept entity = getById(id);
        if (entity == null) {
            throw new RuntimeException("部门不存在");
        }
        entity.setEnabled(!entity.getEnabled());
        updateById(entity);
    }

    @Override
    public List<Long> getThisAndChildIds(Long id) {
        return deptMapper.getThisAndChildIds(id);
    }

    @Override
    public List<Dept> findInIds(List<Long> deptIds) {
        if (CollectionUtil.isEmpty(deptIds))
            return null;

        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BaseEntity::getId, deptIds);
        return list(queryWrapper);
    }

    @Override
    public boolean existAll(List<Long> deptIds) {
        HashSet<Long> ids = new HashSet<>(deptIds);
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BaseEntity::getId, ids);
        return count(queryWrapper) == ids.size();
    }

    @Override
    public List<DeptDto> getDtoListIn(List<Long> ids) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(BaseEntity::getId, ids);
        List<Dept> list = list(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return BeanUtil.copyToList(list, DeptDto.class);
    }
}
