package com.atlantis.mapper;

import com.atlantis.mapper.base.ArticleBaseMapper;
import com.atlantis.pojo.ForumArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumArticleMapper extends ArticleBaseMapper<ForumArticle> {
    public int deleteByIndex(@Param("index") Integer index);

    public List<ForumArticle> getByAuthor(@Param("author") String author);
}
