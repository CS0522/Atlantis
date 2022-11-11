package com.atlantis.service.impl;

import com.atlantis.pojo.Apply;
import com.atlantis.service.ApplyService;
import com.atlantis.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service
// IoC容器管理对象
public class ApplyServiceImpl extends BaseServiceImpl<Apply> implements ApplyService {

    @Override
    public boolean insert(Apply apply) {
        // 查询数据库发现没有同名用户
        if (baseMapper.getByName(apply.getUsername()) == null)
        {
            return (baseMapper.insert(apply) == 1);
        }
        else
        {
            return false;
        }
    }
}
