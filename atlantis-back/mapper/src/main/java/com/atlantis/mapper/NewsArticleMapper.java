package com.atlantis.mapper;

import com.atlantis.mapper.base.ArticleBaseMapper;
import com.atlantis.pojo.NewsArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsArticleMapper extends ArticleBaseMapper<NewsArticle> {
}
