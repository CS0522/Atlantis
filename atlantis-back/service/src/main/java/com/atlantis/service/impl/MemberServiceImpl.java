package com.atlantis.service.impl;

import com.atlantis.mapper.MemberMapper;
import com.atlantis.pojo.Member;
import com.atlantis.service.MemberService;
import com.atlantis.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
// IoC容器管理对象
public class MemberServiceImpl extends BaseServiceImpl<Member> implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public boolean insert(Member member) {
        // 查询数据库发现没有同名
        if (baseMapper.getByName(member.getRealname()) == null)
        {
            return (baseMapper.insert(member) == 1);
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean updatePhoto(Member member) {
        return (memberMapper.updatePhoto(member) == 1);
    }
}
