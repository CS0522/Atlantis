package com.atlantis.service;

import com.atlantis.pojo.UserAdmin;
import java.util.List;

public interface UserAdminService {
    public UserAdmin getById(Integer userId);

    public List<UserAdmin> getAll();

    public boolean insert(UserAdmin userAdmin);

    public boolean delete(Integer userId);
}
