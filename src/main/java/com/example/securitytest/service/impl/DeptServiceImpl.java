package com.example.securitytest.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.securitytest.mapper.DeptMapper;
import com.example.securitytest.pojo.dto.DeptDto;
import com.example.securitytest.pojo.entity.Dept;
import com.example.securitytest.pojo.query.DeptQuery;
import com.example.securitytest.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Create by whz at 2023/7/8
 */

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<DeptDto> list(DeptQuery query) {

        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(query.getName()), Dept::getName, query.getName());
        queryWrapper.eq(query.getParentId() != null, Dept::getParentId, query.getParentId());
        queryWrapper.orderByAsc(Dept::getSort);

        List<Dept> list = list(queryWrapper);

        List<DeptDto> dtoList = BeanUtil.copyToList(list, DeptDto.class);

        for (DeptDto dto : dtoList) {
            long count = count(new LambdaQueryWrapper<Dept>().eq(Dept::getParentId, dto.getId()));
            dto.setHasChildren(count > 0);
        }

        return dtoList;
    }

    @Override
    public Dept save(DeptDto deptDto) {
        if (deptDto.getParentId() != null && deptDto.getParentId().equals(0L) && !isExist(deptDto.getParentId())) {
            throw new RuntimeException("父级不存在");
        }
        if (deptDto.getEnabled() == null) {
            deptDto.setEnabled(true);
        }
        if (deptDto.getSort() == null) {
            deptDto.setSort(99);
        }
        return DeptService.super.save(deptDto);
    }

    @Override
    public boolean updateById(DeptDto deptDto) {
        if (deptDto.getParentId() != null && !deptDto.getParentId().equals(0L) && !isExist(deptDto.getParentId())) {
            throw new RuntimeException("父级不存在");
        }
        return DeptService.super.updateById(deptDto);
    }

    @Override
    public boolean hasChildren(long id) {
        return count(new LambdaQueryWrapper<Dept>().eq(Dept::getParentId, id)) > 0;
    }

    @Override
    public DeptDto afterQueryHandler(DeptDto deptDto) {
        deptDto.setHasChildren(hasChildren(deptDto.getParentId()));
        if (!deptDto.getParentId().equals(0L)){
            Dept parent = getById(deptDto.getParentId());
            if (parent != null){
                deptDto.setParentName(parent.getName());
            }
        }

        return deptDto;
    }

    @Override
    public List<DeptDto> tree(DeptQuery query) {
        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(query.getParentId() != null, Dept::getParentId, query.getParentId());
        queryWrapper.eq(query.getEnabled() != null, Dept::getEnabled, query.getEnabled());
        queryWrapper.like(StrUtil.isNotBlank(query.getName()), Dept::getName, query.getName());

        List<Dept> deptList = list(queryWrapper);

        List<DeptDto> dtoList = BeanUtil.copyToList(deptList, DeptDto.class);

        if (CollectionUtil.isNotEmpty(dtoList) && dtoList.size() == 1){
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

    private List<DeptDto> getChildrenList(DeptDto tree, List<DeptDto> list){
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
        if (entity == null){
            throw new RuntimeException("部门不存在");
        }
        entity.setEnabled(!entity.getEnabled());
        updateById(entity);
    }

    @Override
    public List<Long> getThisAndChildIds(Long id) {
        return deptMapper.getThisAndChildIds(id);
    }

}
