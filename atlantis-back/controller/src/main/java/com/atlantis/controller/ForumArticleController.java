package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.controller.base.ArticleBaseController;
import com.atlantis.exception.ServiceException;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.ForumArticle;
import com.atlantis.service.ForumArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forumArticles")
public class ForumArticleController extends ArticleBaseController<ForumArticle> {
    @Autowired
    private ForumArticleService forumArticleService;

    @GetMapping("/author/{pageNum}/{pageSize}/{author}")
    public Result getByAuthor(@PathVariable Integer pageNum, @PathVariable Integer pageSize,
                              @PathVariable String author)
    {
        try
        {
            PageInfo<ForumArticle> pageInfo = forumArticleService.getByAuthorByPage(
                                                pageNum, pageSize, author);
            Integer code = pageInfo.hasContent() ? Code.GET_OK : Code.GET_ERR;
            String msg = pageInfo.hasContent() ? "get succeeded" : "get failed";

            return new Result(code, pageInfo, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", e, Code.SYS_ERR);
        }
    }

    @GetMapping("/count/{index}")
    public Result getCount(@PathVariable Integer index)
    {
        try
        {
            Integer count = forumArticleService.getCount(index);
            Integer code = Code.GET_OK;
            String msg = "get succeeded";

            return new Result(code, count, msg);
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", e, Code.SYS_ERR);
        }
    }

    @DeleteMapping("/index/{index}")
    public Result deleteByIndex(@PathVariable Integer index)
    {
        // abnormal
        if (index < 0)
        {
            throw new ServiceException("illegal index", Code.SEV_ERR);
        }
        // normal
        try
        {
            boolean result = forumArticleService.deleteByIndex(index);
            return new Result(result ? Code.DELETE_OK : Code.DELETE_ERR,
                    result ? "delete succeeded" : "delete may fail");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }
}
