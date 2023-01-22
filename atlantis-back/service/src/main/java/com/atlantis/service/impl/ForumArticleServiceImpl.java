package com.atlantis.service.impl;

import com.atlantis.mapper.ForumArticleMapper;
import com.atlantis.pojo.ForumArticle;
import com.atlantis.service.CommentService;
import com.atlantis.service.ForumArticleService;
import com.atlantis.service.base.impl.ArticleBaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumArticleServiceImpl extends ArticleBaseServiceImpl<ForumArticle>
        implements ForumArticleService {
    @Autowired
    private ForumArticleMapper forumArticleMapper;

    @Autowired
    private CommentService commentService;

    // 对getById进行重写
    @Override
    public ForumArticle getById(Integer id) {
        // view值+1
        ForumArticle temp = forumArticleMapper.getById(id);
        temp.setView(temp.getView() + 1);
        forumArticleMapper.updateView(temp);

        return (forumArticleMapper.getById(id));
    }

    // 对insert进行重写，检查是否存在相同标题
    @Override
    public boolean insert(ForumArticle forumArticle)
    {
        // 如果没有相同标题，则可以进行插入
        if (articleBaseMapper.getByTitle(forumArticle.getTitle()) == null)
        {
            return (articleBaseMapper.insert(forumArticle) == 1);
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean update(ForumArticle forumArticle)
    {
        // 如果没有相同标题，则可以进行更新
        if (articleBaseMapper.getByTitle(forumArticle.getTitle()) == null)
        {
            return (articleBaseMapper.update(forumArticle) == 1);
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean deleteByIndex(Integer index) {
        // 删除对应评论
        // 获取话题下的所有帖子
        List<ForumArticle> forumArticleList = forumArticleMapper.getByIndex(index, "id asc");
        // 循环删除 comment
        for (ForumArticle forumArticle : forumArticleList) {
            if (!commentService.deleteById(forumArticle.getId()))
            {
                return false;
            }
        }
        // 删除话题下的所有帖子
        forumArticleMapper.deleteByIndex(index);
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        // 删除对应评论
        commentService.deleteById(id);
        // 删除帖子
        return (forumArticleMapper.delete(id) == 1);
    }

    @Override
    public List<ForumArticle> getByAuthor(String author) {
        return (forumArticleMapper.getByAuthor(author));
    }

    @Override
    public PageInfo<ForumArticle> getByAuthorByPage(Integer pageNum, Integer pageSize, String author) {
        PageHelper.startPage(pageNum, pageSize);
        List<ForumArticle> forumArticleList = forumArticleMapper.getByAuthor(author);

        return new PageInfo<ForumArticle>(forumArticleList);
    }

    @Override
    public int getCount(Integer index)
    {
        return (forumArticleMapper.getCount(index));
    }
}
