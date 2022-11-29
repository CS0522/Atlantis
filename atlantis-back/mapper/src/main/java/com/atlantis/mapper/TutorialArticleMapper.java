package com.atlantis.mapper;

import com.atlantis.mapper.base.ArticleBaseMapper;
import com.atlantis.pojo.NewsArticle;
import com.atlantis.pojo.TutorialArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TutorialArticleMapper extends ArticleBaseMapper<TutorialArticle> {
    public int deleteByIndex(@Param("index") Integer index);
}
