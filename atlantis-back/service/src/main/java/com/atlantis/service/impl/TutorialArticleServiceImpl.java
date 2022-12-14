package com.atlantis.service.impl;

import com.atlantis.mapper.TutorialArticleMapper;
import com.atlantis.pojo.ForumArticle;
import com.atlantis.pojo.TutorialArticle;
import com.atlantis.service.TutorialArticleService;
import com.atlantis.service.base.impl.ArticleBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorialArticleServiceImpl extends ArticleBaseServiceImpl<TutorialArticle>
        implements TutorialArticleService {
    @Autowired
    private TutorialArticleMapper tutorialArticleMapper;

    // 对getById进行重写
    @Override
    public TutorialArticle getById(Integer id) {
        // view值+1
        TutorialArticle temp = tutorialArticleMapper.getById(id);
        temp.setView(temp.getView() + 1);
        tutorialArticleMapper.updateView(temp);

        return (tutorialArticleMapper.getById(id));
    }

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

    @Override
    public boolean deleteByIndex(Integer index) {
        // 可能不止一个
        return (tutorialArticleMapper.deleteByIndex(index) != 0);
    }
}
