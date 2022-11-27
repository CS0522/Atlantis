package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.exception.ServiceException;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.Category;
import com.atlantis.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result getAll()
    {
        try
        {
            List<Category> categoryList = categoryService.getAll();
            Integer code = categoryList != null ? Code.GET_OK : Code.GET_ERR;
            String msg = categoryList != null ? "get succeeded" : "get failed";
            return new Result(code, categoryList, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @PostMapping
    public Result insert(@RequestBody Category category)
    {
        if (category == null)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
        }
        try
        {
            // check if exists before insert a new account
            boolean result = categoryService.insert(category);
            return new Result(result ? Code.INSERT_OK : Code.INSERT_ERR, category.getIndex(),
                    result ? "insert succeeded" : "insert failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @DeleteMapping("/{index}")
    public Result delete(@PathVariable Integer index)
    {
        if (index < 0)
        {
            throw new ServiceException("illegal index", Code.SEV_ERR);
        }
        try
        {
            boolean result = categoryService.delete(index);
            return new Result(result ? Code.DELETE_OK : Code.DELETE_ERR,
                    result ? "delete succeeded" : "delete failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }
}
