package com.yo.newsapp.repository

import com.yo.newsapp.model.Article
import com.yo.newsapp.repository.db.ArticleDatabase
import com.yo.newsapp.repository.service.RetrofitClient

class NewsRepository(val db: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitClient.api.getBreakingNews(countryCode,pageNumber)

    suspend fun getSearchNews(q: String, pageNumber: Int) =
        RetrofitClient.api.getSearchNews( q, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().insert(article)
    suspend fun delete(article: Article) = db.getArticleDao().deleteArticle(article)

    fun getAllArticles() = db.getArticleDao().getArticles()
}