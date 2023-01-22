package com.atlantis.mapper;

import com.atlantis.pojo.Comment;
import com.atlantis.pojo.CommentJoinForumArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    public List<CommentJoinForumArticle> getAll();

    public Comment getByIndex(@Param("index") Integer index);

    public List<Comment> getById(@Param("id") Integer id);

    public List<Comment> getByName(@Param("name") String name,
                                   @Param("loginType") String loginType);

    public int insert(Comment comment);

    public int update(Comment comment);

    public int delete(@Param("index") Integer index);

    public int deleteById(@Param("id") Integer id);

    public List<CommentJoinForumArticle> fuzzyQuery(@Param("searchKey") String searchKey);
}
