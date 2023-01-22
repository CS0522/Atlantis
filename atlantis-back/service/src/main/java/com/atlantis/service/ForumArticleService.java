package com.atlantis.service;

import com.atlantis.pojo.ForumArticle;
import com.atlantis.service.base.ArticleBaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ForumArticleService extends ArticleBaseService<ForumArticle> {
    public boolean deleteByIndex(Integer index);

    public List<ForumArticle> getByAuthor(String author);

    public PageInfo<ForumArticle> getByAuthorByPage(Integer pageNum, Integer pageSize, String author);

    public int getCount(Integer index);
}
