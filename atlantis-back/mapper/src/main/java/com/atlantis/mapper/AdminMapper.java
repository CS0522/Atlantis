package com.atlantis.mapper;

import com.atlantis.mapper.base.BaseMapper;
import com.atlantis.pojo.Admin;
import org.apache.ibatis.annotations.*;


@Mapper
// 自动代理
public interface AdminMapper extends BaseMapper<Admin> {

}
