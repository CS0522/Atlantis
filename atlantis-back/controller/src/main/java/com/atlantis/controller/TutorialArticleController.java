package com.atlantis.controller;

import com.atlantis.controller.base.ArticleBaseController;
import com.atlantis.pojo.TutorialArticle;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tutorialArticles")
public class TutorialArticleController extends ArticleBaseController<TutorialArticle> {
}
