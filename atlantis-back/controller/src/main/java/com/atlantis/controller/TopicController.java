package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.exception.ServiceException;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.Topic;
import com.atlantis.pojo.TopicJoinForumArticle;
import com.atlantis.service.TopicService;
import com.github.pagehelper.PageInfo;
import net.sf.jsqlparser.statement.select.Top;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping
    public Result getAll()
    {
        try
        {
            List<Topic> topicList = topicService.getAll();
            Integer code = topicList != null ? Code.GET_OK : Code.GET_ERR;
            String msg = topicList != null ? "get succeeded" : "get failed";
            return new Result(code, topicList, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @GetMapping("/{pageNum}/{pageSize}")
    public Result getAllByPage(@PathVariable Integer pageNum, @PathVariable Integer pageSize)
    {
        try
        {
            PageInfo<Topic> pageInfo = topicService.getAllByPage(pageNum, pageSize);
            Integer code = pageInfo != null ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo != null ? "get succeeded" : "get failed";

            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @GetMapping("/{index}")
    public Result getByIndex(@PathVariable Integer index)
    {
        try
        {
            Topic topic = topicService.getByIndex(index);
            Integer code = topic != null ? Code.GET_OK : Code.GET_ERR;
            String msg = topic != null ? "get succeeded" : "get failed";
            return new Result(code, topic, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @GetMapping("/search/{search}/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable String search,
                             @PathVariable Integer pageNum, @PathVariable Integer pageSize)
    {
//        System.out.println("finding...");
        try
        {
            PageInfo<TopicJoinForumArticle> pageInfo = topicService.findByPage(pageNum, pageSize, search);
            Integer code = pageInfo != null ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo != null ? "get succeeded" : "get failed";
            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", e, Code.SYS_ERR);
        }
    }

    @GetMapping("/count/{pageNum}/{pageSize}")
    public Result getCount(@PathVariable Integer pageNum, @PathVariable Integer pageSize)
    {
        try
        {
            PageInfo<TopicJoinForumArticle> pageInfo =
                    topicService.getCountByPage(pageNum, pageSize);
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
    public Result insert(@RequestBody Topic topic)
    {
        if (topic == null)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
        }
        try
        {
            // check if exists before insert a new account
            boolean result = topicService.insert(topic);
            return new Result(result ? Code.INSERT_OK : Code.INSERT_ERR, topic.getIndex(),
                    result ? "insert succeeded" : "insert failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @PutMapping
    public Result update(@RequestBody Topic topic)
    {
        if (topic == null)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
        }
        try
        {
            // check if exists before insert a new account
            boolean result = topicService.update(topic);
            return new Result(result ? Code.UPDATE_OK : Code.UPDATE_ERR,
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
            boolean result = topicService.delete(index);
            return new Result(result ? Code.DELETE_OK : Code.DELETE_ERR,
                    result ? "delete succeeded" : "delete failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }
}
