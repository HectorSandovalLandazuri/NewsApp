package com.example.newsapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.MainApp
import com.example.newsapp.models.ArticleCategory
import com.example.newsapp.models.TopNewsResponse
import com.example.newsapp.models.getArticleCategory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository = getApplication<MainApp>().repository

    private val _newsResponse = MutableStateFlow(TopNewsResponse())
    val newsResponse: StateFlow<TopNewsResponse>
    get()= _newsResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean>
    get() = _isError

    val errorHandler = CoroutineExceptionHandler {
        _, error ->
            if (error is Exception) {
                _isError.value = true
            }
    }

    fun getTopArticles() {
        _isLoading.value=true
        viewModelScope.launch (Dispatchers.IO + errorHandler) {
            _newsResponse.value = repository.getArticles()
            _isLoading.value = false
        }
    }

    private val _getArticleByCategory  = MutableStateFlow(TopNewsResponse())
    val getArticleByCategory: StateFlow<TopNewsResponse>
        get() =  _getArticleByCategory

    private val _selectedCategory:  MutableStateFlow<ArticleCategory?> = MutableStateFlow(null)
    val selectedCategory: StateFlow<ArticleCategory?>
        get() =  _selectedCategory


    fun getArticlesByCategory(category: String) {
        _isLoading.value=true
        viewModelScope.launch (Dispatchers.IO + errorHandler) {
            _getArticleByCategory.value = repository.getArticlesByCategory(category)
            _isLoading.value = false
        }
    }

    val sourceName = MutableStateFlow("engadget")
    private val _getArticlesBySource = MutableStateFlow(TopNewsResponse())
    val getArticlesBySource: StateFlow<TopNewsResponse>
    get() = _getArticlesBySource

    val query = MutableStateFlow("")
    private val _searchedNewsResponse = MutableStateFlow(TopNewsResponse())
    val searchedNewsResponse: StateFlow<TopNewsResponse>
        get() = _searchedNewsResponse

    fun onSelectedCategoryChanged (category: String) {
        val newCategory = getArticleCategory(category)
        _selectedCategory.value = newCategory
    }

    fun getArticlesBySource() {
        _isLoading.value=true
        viewModelScope.launch (Dispatchers.IO + errorHandler) {
            _getArticlesBySource.value = repository.getArticlesBySource(sourceName.value)
            _isLoading.value = false
        }
    }

    fun getSearchedArticles(query: String) {
        _isLoading.value=true
        viewModelScope.launch (Dispatchers.IO + errorHandler) {
            _searchedNewsResponse.value = repository.getSearchedArticles(query = query)
            _isLoading.value = false
        }
    }




}