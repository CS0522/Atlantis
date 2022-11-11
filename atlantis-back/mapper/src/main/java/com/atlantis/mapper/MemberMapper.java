package com.atlantis.mapper;

import com.atlantis.mapper.base.BaseMapper;
import com.atlantis.pojo.Member;
import org.apache.ibatis.annotations.Mapper;


@Mapper
// 自动代理
public interface MemberMapper extends BaseMapper<Member> {

}
