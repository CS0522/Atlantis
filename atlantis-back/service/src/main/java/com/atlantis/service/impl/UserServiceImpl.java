package com.atlantis.service.impl;

import com.atlantis.pojo.User;
import com.atlantis.service.UserService;
import com.atlantis.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
// IoC容器管理对象
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Override
    public boolean updateInfo(User user) {
        return (baseMapper.updateInfo(user) == 1);
    }

    @Override
    public boolean updatePwd(User user) {
        return (baseMapper.updatePwd(user) == 1);
    }

    @Override
    public boolean insert(User user) {
        // 查询数据库发现没有同名用户
        if (baseMapper.getByName(user.getUsername()) == null)
        {
            return (baseMapper.insert(user) == 1);
        }
        else
        {
            return false;
        }
    }

    @Override
    public User login(User login)
    {
        // TODO login function
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
