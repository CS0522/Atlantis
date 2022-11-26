package com.atlantis.service.impl;

import com.atlantis.pojo.NewsArticle;
import com.atlantis.service.NewsArticleService;
import com.atlantis.service.base.impl.ArticleBaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NewsArticleServiceImpl extends ArticleBaseServiceImpl<NewsArticle>
        implements NewsArticleService {
    // 对insert进行重写，检查是否存在相同标题
    public boolean insert(NewsArticle newsArticle)
    {
        // 如果没有相同标题，则可以进行插入
        if (articleBaseMapper.getByTitle(newsArticle.getTitle()) == null)
        {
            return (articleBaseMapper.insert(newsArticle) == 1);
        }
        else
        {
            return false;
        }
    }
}
