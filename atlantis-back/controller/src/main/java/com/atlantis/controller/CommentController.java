package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.exception.ServiceException;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.Comment;
import com.atlantis.pojo.CommentJoinForumArticle;
import com.atlantis.service.CommentService;
import com.github.pagehelper.PageInfo;
import org.springframework.aop.scope.ScopedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.CDATASection;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/{pageNum}/{pageSize}")
    public Result getAll(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        try
        {
            PageInfo<CommentJoinForumArticle> pageInfo = commentService.getAllByPage(pageNum, pageSize);
            Integer code = pageInfo != null ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo != null ? "get succeeded" : "get failed";

            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @GetMapping("/index/{index}")
    public Result getByIndex(@PathVariable Integer index)
    {
        try
        {
            Comment comment = commentService.getByIndex(index);
            Integer code = comment != null ? Code.GET_OK : Code.GET_ERR;
            String msg = comment != null ? "get succeeded" : "get failed";

            return new Result(code, comment, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @GetMapping("/id/{pageNum}/{pageSize}/{id}")
    public Result getById(@PathVariable Integer pageNum, @PathVariable Integer pageSize,
                          @PathVariable Integer id)
    {
        try
        {
            PageInfo<Comment> pageInfo = commentService.getByIdByPage(pageNum, pageSize, id);
            Integer code = pageInfo != null ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo != null ? "get succeeded" : "get failed";

            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @GetMapping("/name/{pageNum}/{pageSize}/{name}/{loginType}")
    public Result getByName(@PathVariable Integer pageNum, @PathVariable Integer pageSize,
                            @PathVariable String name, @PathVariable String loginType)
    {
        try
        {
            PageInfo<Comment> pageInfo = commentService.getByNameByPage(pageNum, pageSize,
                                                                    name, loginType);
            Integer code = pageInfo != null ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo != null ? "get succeeded" : "get failed";

            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @GetMapping("/search/{searchKey}/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable String searchKey,
                             @PathVariable Integer pageNum, @PathVariable Integer pageSize)
    {
        try
        {
            PageInfo<CommentJoinForumArticle> pageInfo = commentService.findByPage(pageNum, pageSize, searchKey);
            Integer code = pageInfo != null ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo != null ? "get succeeded" : "get failed";

            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @PostMapping
    public Result insert(@RequestBody Comment comment)
    {
        if (comment == null)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
        }
        try
        {
            boolean result = commentService.insert(comment);

            return new Result(result ? Code.INSERT_OK : Code.INSERT_ERR, comment.getIndex(),
                                result ? "insert succeeded" : "insert failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @PutMapping
    public Result update(@RequestBody Comment comment)
    {
        if (comment == null)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
        }
        try
        {
            boolean result = commentService.update(comment);

            return new Result(result ? Code.UPDATE_OK : Code.UPDATE_ERR,
                            result ? "update succeeded" : "update failed");
        }
        catch (SystemException e)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
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
            boolean result = commentService.delete(index);
            return new Result(result ? Code.DELETE_OK : Code.DELETE_ERR,
                    result ? "delete succeeded" : "delete failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }
}
