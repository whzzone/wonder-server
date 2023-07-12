package com.gitee.whzzone.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.whzzone.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : whz
 * @date : 2023/5/16 19:33
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
