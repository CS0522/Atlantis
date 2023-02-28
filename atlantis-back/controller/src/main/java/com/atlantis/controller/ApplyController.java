package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.controller.base.BaseController;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.Admin;
import com.atlantis.pojo.Apply;
import com.atlantis.pojo.User;
import com.atlantis.pojo.UserAdmin;
import com.atlantis.service.AdminService;
import com.atlantis.service.ApplyService;
import com.atlantis.service.UserAdminService;
import com.atlantis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applies")
public class ApplyController extends BaseController<Apply> {
    @Autowired
    private ApplyService applyService;

    @GetMapping("/agree/{id}")
    public Result agreeApply(@PathVariable Integer id)
    {
        // 确认id正确，在apply中
        try
        {
            if (baseService.getById(id) == null)
            {
                return new Result(Code.GET_ERR, null, "null id");
            }

            boolean result = applyService.agreeApply(id);

            return new Result(result ? Code.GET_OK : Code.GET_ERR, null,
                    result ? "succeeded" : "failed");
        }
        catch (Exception e)
        {
            throw new SystemException("unknown exception occurred", e, Code.SYS_ERR);
        }
    }

    @DeleteMapping("/refuse/{id}")
    public Result refuseApply(@PathVariable Integer id)
    {
        // 确认id正确，在apply中
        try
        {
            if (baseService.getById(id) == null)
            {
                return new Result(Code.DELETE_ERR, null, "null id");
            }

            boolean result = applyService.refuseApply(id);

            return new Result(result ? Code.DELETE_OK : Code.DELETE_ERR, null,
                    result ? "succeeded" : "failed");
        }
        catch (Exception e)
        {
            throw new SystemException("unknown exception occurred", e, Code.SYS_ERR);
        }
    }

}
