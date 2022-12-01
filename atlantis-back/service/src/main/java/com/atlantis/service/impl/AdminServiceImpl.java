package com.atlantis.service.impl;

import com.atlantis.pojo.Admin;
import com.atlantis.service.AdminService;
import com.atlantis.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service
// IoC容器管理对象
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Override
    public boolean updateInfo(Admin admin) {
        return (baseMapper.updateInfo(admin) == 1);
    }

    @Override
    public boolean updatePwd(Admin admin) {
        return (baseMapper.updatePwd(admin) == 1);
    }

    @Override
    public boolean insert(Admin admin) {
        // 查询数据库发现没有同名用户
        if (baseMapper.getByName(admin.getUsername()) == null)
        {
            return (baseMapper.insert(admin) == 1);
        }
        else
        {
            return false;
        }
    }

    @Override
    public Admin login(Admin login)
    {
        /*
            1. 密码进行加密处理
            2. 查询数据库
            3. 无结果，返回失败 == Code.LOGIN_ERR
            4. 密码不一致，返回失败 == Code.LOGIN_ERR
            5. 员工状态，返回失败 == STATUS_OFF
            6. 登陆成功，将id存储到session中 == Code.LOGIN_OK
        */
        return (baseMapper.getByUsernameAndPassword(login.getUsername(), login.getPassword()));

    }

}
