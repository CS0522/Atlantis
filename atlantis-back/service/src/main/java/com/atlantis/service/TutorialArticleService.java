package com.atlantis.service;

import com.atlantis.pojo.TutorialArticle;
import com.atlantis.service.base.ArticleBaseService;

public interface TutorialArticleService extends ArticleBaseService<TutorialArticle> {
    public boolean deleteByIndex(Integer index);
}
