package com.epl.blog.service;

import com.epl.blog.models.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    public void save(Article article);

    public boolean isExist(String title);

    public List<Article> getAllArticles();

}