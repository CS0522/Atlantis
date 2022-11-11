package com.atlantis.service.impl;

import com.atlantis.mapper.UserAdminMapper;
import com.atlantis.pojo.UserAdmin;
import com.atlantis.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAdminServiceImpl implements UserAdminService {
    @Autowired
    private UserAdminMapper userAdminMapper;

    @Override
    public UserAdmin getById(Integer userId) {
        return (userAdminMapper.getById(userId));
    }

    @Override
    public List<UserAdmin> getAll() {
        return (userAdminMapper.getAll());
    }

    @Override
    public boolean insert(UserAdmin userAdmin) {
        return (userAdminMapper.insert(userAdmin.getUserId(), userAdmin.getAdminId()) == 1);
    }

    @Override
    public boolean delete(Integer userId) {
        return (userAdminMapper.delete(userId) == 1);
    }
}
