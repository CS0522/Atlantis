package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.exception.ServiceException;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.UserMessage;
import com.atlantis.service.UserMessageService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usermessages")
public class UserMessageController {
    @Autowired
    private UserMessageService userMessageService;

    @GetMapping("/index/{index}")
    public Result getByIndex(@PathVariable("index") Integer index)
    {
        try
        {
            UserMessage userMessage = userMessageService.getByIndex(index);
            Integer code = userMessage != null ? Code.GET_OK : Code.GET_ERR;
            String msg = userMessage != null ? "get succeeded" : "get failed";

            return new Result(code, userMessage, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @GetMapping("/{pageNum}/{pageSize}")
    public Result getAllByPage(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize)
    {
        try
        {
            PageInfo<UserMessage> pageInfo = userMessageService.getAllByPage(pageNum, pageSize);
            Integer code = pageInfo.hasContent() ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo.hasContent() ? "get succeeded" : "get failed";

            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @GetMapping("/destination/{pageNum}/{pageSize}/{destination}")
    public Result getByDestinationByPage(@PathVariable("pageNum") Integer pageNum,
                                         @PathVariable("pageSize") Integer pageSize,
                                         @PathVariable("destination") Integer destination)
    {
        try
        {
            PageInfo<UserMessage> pageInfo = userMessageService.getByDestinationByPage(
                                            pageNum, pageSize, destination);
            Integer code = pageInfo.hasContent() ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo.hasContent() ? "get succeeded" : "get failed";

            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @PostMapping
    public Result insert(@RequestBody UserMessage userMessage)
    {
        if (userMessage == null)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
        }
        try
        {
            // check if exists before insert a new account
            boolean result = userMessageService.insert(userMessage);
            return new Result(result ? Code.INSERT_OK : Code.INSERT_ERR, userMessage.getIndex(),
                    result ? "insert succeeded" : "insert failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }

    @PutMapping
    public Result updateStatus(@RequestBody UserMessage userMessage)
    {
        if (userMessage == null)
        {
            throw new ServiceException("null object", Code.SEV_ERR);
        }
        try
        {
            boolean result = userMessageService.updateStatus(userMessage);

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
            boolean result = userMessageService.delete(index);
            return new Result(result ? Code.DELETE_OK : Code.DELETE_ERR,
                    result ? "delete succeeded" : "delete failed");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }
}
