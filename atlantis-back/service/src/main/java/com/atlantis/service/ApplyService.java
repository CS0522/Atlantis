package com.atlantis.service;

import com.atlantis.pojo.Apply;
import com.atlantis.service.base.BaseService;

public interface ApplyService extends BaseService<Apply> {
    public boolean agreeApply(Integer id);

    public boolean refuseApply(Integer id);
}
