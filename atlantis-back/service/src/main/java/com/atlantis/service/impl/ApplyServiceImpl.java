package com.atlantis.service.impl;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.mapper.AdminMapper;
import com.atlantis.mapper.UserAdminMapper;
import com.atlantis.mapper.UserMapper;
import com.atlantis.mapper.UserMessageMapper;
import com.atlantis.pojo.*;
import com.atlantis.service.ApplyService;
import com.atlantis.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
// IoC容器管理对象
public class ApplyServiceImpl extends BaseServiceImpl<Apply> implements ApplyService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserAdminMapper userAdminMapper;

    @Autowired
    private UserMessageMapper userMessageMapper;

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

    boolean sendMessage(Integer userId, boolean flag) {
        UserMessage userMessage = new UserMessage();
        userMessage.setDestination(userId);
        userMessage.setSource(-1);
        if (flag)
        {
            userMessage.setTitle("管理员申请已通过！");
        }
        else
        {
            userMessage.setTitle("管理员申请被拒绝！");
        }
        userMessage.setContent("");

        return (userMessageMapper.insert(userMessage) == 1);
    }

    @Override
    public boolean refuseApply(Integer id) {
        // send message
        return (baseMapper.delete(id) == 1
                && sendMessage(id, false));
    }

    @Override
    public boolean agreeApply(Integer id) {
        // 根据id获取用户数据
        User user = userMapper.getById(id);
        // admin item
        Admin admin = new Admin();
        admin.setUsername(user.getUsername());
        admin.setPassword(user.getPassword());
        // 加入 admin 表，密码、名字保持不变，返回id主键
        // 如果插入的是否发现已经存在，返回失败
        if (adminMapper.insert(admin) != 1){
            return false;
        }

        Integer adminId = admin.getId();
        // id 添加到 user_admin 表中
        UserAdmin userAdmin = new UserAdmin();
        userAdmin.setUserId(id);
        userAdmin.setAdminId(adminId);
        // send message
        return (userAdminMapper.insert(userAdmin.getUserId(), userAdmin.getAdminId()) == 1
                && sendMessage(id, true));
    }
}
