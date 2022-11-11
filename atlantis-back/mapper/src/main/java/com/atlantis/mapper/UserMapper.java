package com.atlantis.mapper;

import com.atlantis.mapper.base.BaseMapper;
import com.atlantis.pojo.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
// 自动代理
public interface UserMapper extends BaseMapper<User> {

}
