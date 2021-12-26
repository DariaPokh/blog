package com.epl.blog.service;

import com.epl.blog.models.Article;
import com.epl.blog.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public void save(Article article) {
        articleRepository.save(article);

    }

    @Override
    public boolean isExist(String title) {
        List<Article> articles = articleRepository.findAll();
        for (Article el : articles) {
            if (el.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }
}
