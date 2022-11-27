package com.atlantis.mapper;

import com.atlantis.mapper.base.ArticleBaseMapper;
import com.atlantis.pojo.NewsArticle;
import com.atlantis.pojo.TutorialArticle;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TutorialArticleMapper extends ArticleBaseMapper<TutorialArticle> {
}
