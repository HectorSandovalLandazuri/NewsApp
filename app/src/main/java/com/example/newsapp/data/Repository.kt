package com.example.newsapp.data

import com.example.newsapp.models.ArticleCategory
import com.example.newsapp.network.NewsManager

class Repository (val manager:NewsManager) {
    suspend fun getArticles() = manager.getArticles(country = "mx")
    suspend fun getArticlesByCategory(category: String)=manager.getArticlesByCategory(category)
    suspend fun getArticlesBySource(source:String) = manager.getArticlesBySource(source = source)
    suspend fun getSearchedArticles(query:String) = manager.getSearchedArticles(query = query)
}