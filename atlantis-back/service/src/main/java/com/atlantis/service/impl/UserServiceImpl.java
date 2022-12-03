package com.atlantis.service.impl;

import com.atlantis.pojo.User;
import com.atlantis.service.UserService;
import com.atlantis.service.base.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
// IoC容器管理对象
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Override
    public boolean updateInfo(User user) {
        // 当不存在该用户名时
        // 或者存在该用户名但是是要修改的用户本身
        if (baseMapper.getByName(user.getUsername()) == null ||
                Objects.equals(baseMapper.getByName(user.getUsername()).getId(), user.getId()))
        {
            return (baseMapper.updateInfo(user) == 1);
        }
        else
        {
            return false;
        }
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
    public boolean update(User user) {
        // 查询数据库发现没有同名用户
        // 当不存在该用户名时
        // 或者存在该用户名但是是要修改的用户本身
        if (baseMapper.getByName(user.getUsername()) == null ||
                Objects.equals(baseMapper.getByName(user.getUsername()).getId(), user.getId()))
        {
            return (baseMapper.update(user) == 1);
        }
        else
        {
            return false;
        }
    }

    @Override
    public User login(User login)
    {
        /*
            1. 密码进行加密处理
            2. 查询数据库
            3. 无结果，返回失败 == Code.LOGIN_ERR
            4. 密码不一致，返回失败 == Code.LOGIN_ERR
            5. 员工状态，返回失败 == STATUS_OFF
            6. 登陆成功，将id存储到session中 == Code.LOGIN_OK
        */
        System.out.println("login function in service impl...");
        return (baseMapper.getByUsernameAndPassword(login.getUsername(), login.getPassword()));

    }
}
