package com.atlantis.controller.base;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.exception.ServiceException;
import com.atlantis.exception.SystemException;
import com.atlantis.service.base.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class BaseController<T> {
    @Autowired
    protected BaseService<T> baseService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) // 从url路径获取id
    {
        // 设置异常情况
        if (id < 0)
        {
            throw new ServiceException("illegal id", Code.SEV_ERR);
        }
        // normal
        T obj = baseService.getById(id);
        Integer code = obj != null ? Code.GET_OK : Code.GET_ERR;
        String msg = obj != null ? "get succeeded" : "get failed";
       return new Result(code, obj, msg);
    }

    @GetMapping
    public Result getAll()
    {
        try
        {
            List<T> objs = baseService.getAll();
            Integer code = objs != null ? Code.GET_OK : Code.GET_ERR;
            String msg = objs != null ? "get succeeded" : "get failed";
            return new Result(code, objs, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    // 分页查询
    @GetMapping("/{pageNum}/{pageSize}")
    public Result getAllByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize)
    {
        try
        {
            PageInfo<T> pageInfo = baseService.getAllByPage(pageNum, pageSize);
            Integer code = pageInfo.hasContent() ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo.hasContent() ? "get succeeded" : "get failed";
            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", e, Code.SYS_ERR);
        }
    }

    // 模糊查询
    @GetMapping("/{name}/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable String name,
                             @PathVariable Integer pageNum, @PathVariable Integer pageSize)
    {
//        System.out.println("finding...");
        try
        {
            PageInfo<T> pageInfo = baseService.findByPage(pageNum, pageSize, name);
            Integer code = pageInfo.hasContent() ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo.hasContent() ? "get succeeded" : "get failed";
            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", e, Code.SYS_ERR);
        }
    }

    @PostMapping
    public Result insert(@RequestBody T obj)
    {
        // 设置异常情况，获取到的obj == null
        if (obj == null)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
        }
        // normal
        try
        {
            // check if exists before insert a new account
            boolean result = baseService.insert(obj);
            return new Result(result ? Code.INSERT_OK : Code.INSERT_ERR,
                                result ? "insert succeeded" : "insert failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @PutMapping
    public Result update(@RequestBody T obj)
    {
        // 设置异常情况，获取到的obj == null
        if (obj == null)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
        }
        // normal
        try
        {
            boolean result = baseService.update(obj);
            return new Result(result ? Code.UPDATE_OK : Code.UPDATE_ERR,
                                result ? "update succeeded" : "update failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    // 将更新操作的代码进行复用
    public Result updateByType(T obj, String updateType)
    {
        // abnormal
        if (obj == null)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
        }
        // normal
        try
        {
            boolean result = false;
            // 根据info的值进行不同的更新操作
            if ("info".equals(updateType))
            {
                result = baseService.updateInfo(obj);
            }
            else if ("pwd".equals(updateType))
            {
                result = baseService.updatePwd(obj);
            }
            return new Result(result ? Code.UPDATE_OK : Code.UPDATE_ERR,
                    result ? "update succeeded" : "update failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id)
    {
        // abnormal
        if (id < 0)
        {
            throw new ServiceException("illegal id", Code.SEV_ERR);
        }
        // normal
        try
        {
            boolean result = baseService.delete(id);
            return new Result(result ? Code.DELETE_OK : Code.DELETE_ERR,
                    result ? "delete succeeded" : "delete failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }
}
