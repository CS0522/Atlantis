package com.atlantis.service.impl;

import com.atlantis.pojo.TutorialArticle;
import com.atlantis.service.TutorialArticleService;
import com.atlantis.service.base.impl.ArticleBaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TutorialArticleServiceImpl extends ArticleBaseServiceImpl<TutorialArticle>
        implements TutorialArticleService {
    // 对insert进行重写，检查是否存在相同标题
    public boolean insert(TutorialArticle TutorialArticle)
    {
        // 如果没有相同标题，则可以进行插入
        if (articleBaseMapper.getByTitle(TutorialArticle.getTitle()) == null)
        {
            return (articleBaseMapper.insert(TutorialArticle) == 1);
        }
        else
        {
            return false;
        }
    }
}
