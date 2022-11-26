package com.atlantis.controller;

import com.atlantis.controller.base.ArticleBaseController;
import com.atlantis.pojo.NewsArticle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newsArticles")
public class NewsArticleController extends ArticleBaseController<NewsArticle> {
}
