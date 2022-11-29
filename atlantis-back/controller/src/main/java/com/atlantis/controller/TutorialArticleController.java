package com.atlantis.controller;

import com.atlantis.common.Code;
import com.atlantis.common.Result;
import com.atlantis.controller.base.ArticleBaseController;
import com.atlantis.exception.ServiceException;
import com.atlantis.exception.SystemException;
import com.atlantis.pojo.TutorialArticle;
import com.atlantis.service.TutorialArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tutorialArticles")
public class TutorialArticleController extends ArticleBaseController<TutorialArticle> {
    @Autowired
    private TutorialArticleService tutorialArticleService;

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
            boolean result = tutorialArticleService.deleteByIndex(index);
            return new Result(result ? Code.DELETE_OK : Code.DELETE_ERR,
                    result ? "delete succeeded" : "delete may fail");
        }
        catch (SystemException e)
        {
            throw new SystemException("unknown error occurred", Code.SYS_ERR);
        }
    }
}
