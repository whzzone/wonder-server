package com.example.securitytest.service;

import com.example.securitytest.pojo.dto.DeptDto;
import com.example.securitytest.pojo.entity.Dept;
import com.example.securitytest.pojo.query.DeptQuery;
import com.example.securitytest.pojo.vo.PageData;

/**
 * @author Create by whz at 2023/7/8
 */
public interface DeptService extends IEntityService<Dept, DeptDto> {

    PageData<DeptDto> page(DeptQuery query);


}
