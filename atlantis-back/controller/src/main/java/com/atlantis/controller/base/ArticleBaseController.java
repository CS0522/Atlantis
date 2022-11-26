package com.atlantis.controller.base;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.exception.ServiceException;
import com.atlantis.exception.SystemException;
import com.atlantis.service.base.ArticleBaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public abstract class ArticleBaseController<T>{
    @Autowired
    protected ArticleBaseService<T> articleBaseService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) // 从url路径获取id
    {
        // 设置异常情况
        if (id < 0)
        {
            throw new ServiceException("illegal id", Code.SEV_ERR);
        }
        // normal
        T obj = articleBaseService.getById(id);
        Integer code = obj != null ? Code.GET_OK : Code.GET_ERR;
        String msg = obj != null ? "get succeeded" : "get failed";
        return new Result(code, obj, msg);
    }

    @GetMapping("/{pageNum}/{pageSize}/{order}")
    public Result getAllByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize,
                               @PathVariable String order)
    {
        try
        {
            PageInfo<T> pageInfo = articleBaseService.getAllByPage(pageNum, pageSize, order);
            Integer code = pageInfo != null ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo != null ? "get succeeded" : "get failed";
            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", e, Code.SYS_ERR);
        }
    }

    // 分页查询
    @GetMapping("/{index}/{pageNum}/{pageSize}/{order}")
    public Result getByIndexByPage(@PathVariable Integer index,
                               @PathVariable Integer pageNum, @PathVariable Integer pageSize,
                               @PathVariable String order)
    {
        try
        {
            PageInfo<T> pageInfo = articleBaseService.getByIndexByPage(pageNum, pageSize, index, order);
            Integer code = pageInfo != null ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo != null ? "get succeeded" : "get failed";
            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", e, Code.SYS_ERR);
        }
    }

    // 模糊查询
    @GetMapping("/search/{searchKey}/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable String searchKey,
                             @PathVariable Integer pageNum, @PathVariable Integer pageSize)
    {
        try
        {
            PageInfo<T> pageInfo = articleBaseService.findByPage(pageNum, pageSize, searchKey);
            Integer code = pageInfo != null ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo != null ? "get succeeded" : "get failed";
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
            boolean result = articleBaseService.insert(obj);
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
            boolean result = articleBaseService.update(obj);
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
            boolean result = articleBaseService.delete(id);
            return new Result(result ? Code.DELETE_OK : Code.DELETE_ERR,
                    result ? "delete succeeded" : "delete failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }
}
