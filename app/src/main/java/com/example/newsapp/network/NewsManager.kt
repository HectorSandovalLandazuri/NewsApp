package com.example.newsapp.network


import androidx.compose.runtime.*
import com.example.newsapp.models.ArticleCategory
import com.example.newsapp.models.TopNewsResponse
import com.example.newsapp.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsManager(private val service: NewsService) {
    private val _newsResponse  = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsResponse
        }

    val sourceName= mutableStateOf("abc-news")

    private val _getArticleBySource  = mutableStateOf(TopNewsResponse())
    val getArticleBySource: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }



    val query = mutableStateOf("")
    private val _searchedNewsResponse  = mutableStateOf(TopNewsResponse())
    val searchedNewsResponse: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _searchedNewsResponse
        }



    val selectedCategory:MutableState<ArticleCategory?> = mutableStateOf(null)


    suspend fun getArticles(country: String):TopNewsResponse = withContext(Dispatchers.IO){
        service.getTopArticles(country = country)

    }

    suspend fun getArticlesByCategory(category: String):TopNewsResponse = withContext(Dispatchers.IO){
        service.getArticlesByCategory(category = category)


    }

    suspend fun getArticlesBySource(source:String): TopNewsResponse = withContext(Dispatchers.IO){
        service.getArticlesBySources(source)
    }

    suspend fun getSearchedArticles(query:String): TopNewsResponse = withContext(Dispatchers.IO){
        service.getArticles(query)
    }


    fun onSelectedCategoryChanged(category:String){
        val newCategory= getArticleCategory(category=category)
        selectedCategory.value=newCategory
    }

}