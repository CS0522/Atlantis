package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.Category;
import com.atlantis.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
