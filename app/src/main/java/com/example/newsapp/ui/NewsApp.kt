package com.example.newsapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.BottomMenuScreen
import com.example.newsapp.components.BottomMenu
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.ui.screen.Categories
import com.example.newsapp.ui.screen.DetailScreen
import com.example.newsapp.ui.screen.Sources
import com.example.newsapp.ui.screen.TopNews


@Composable
fun NewsApp(mainViewModel: MainViewModel){
    val scrollState = rememberScrollState()
    val navController= rememberNavController()
    MainScreen(navController,scrollState, mainViewModel = mainViewModel)
}

@Composable
fun MainScreen(navController: NavHostController,scrollState: ScrollState, mainViewModel: MainViewModel){
    Scaffold(bottomBar = {
        BottomMenu(navController )
    }) {
        Navigation(navController = navController,scrollState = scrollState, paddingValues = it,
        viewModel = mainViewModel)

    }
}

@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState, paddingValues: PaddingValues, viewModel: MainViewModel ){

    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.isError.collectAsState()
    val articles=  mutableListOf(TopNewsArticle())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.clear()
    articles.addAll(topArticles?: listOf())

    articles.let {
        NavHost(navController = navController, startDestination =
        BottomMenuScreen.TopNews.route, modifier = Modifier.padding(paddingValues)){
            val queryState = mutableStateOf(viewModel.query.value)
            val isLoading = mutableStateOf(loading)
            val isError = mutableStateOf(error)
            bottomNavigation(navController=navController,articles,query = queryState,  viewModel,isLoading = isLoading, isError = isError)
            composable("Detail/{index}",
                arguments = listOf(navArgument("index"){type = NavType.IntType})) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    articles.clear()
                    if(queryState.value != "") {
                        articles.addAll(viewModel.searchedNewsResponse.value.articles?: listOf())
                    } else {
                        articles.addAll(viewModel.newsResponse.value.articles?:listOf())
                    }
                    val article = articles[index]
                    DetailScreen(article, scrollState, navController)
                }
            }
        }
    }
}


fun NavGraphBuilder.bottomNavigation(navController:NavController,articles:List<TopNewsArticle>, query: MutableState<String>, viewModel: MainViewModel, isLoading: MutableState<Boolean>, isError: MutableState<Boolean>){
    composable(BottomMenuScreen.TopNews.route){
        TopNews(navController=navController, articles,query, viewModel = viewModel, isLoading = isLoading, isError = isError)
    }
    composable(BottomMenuScreen.Categories.route){
        viewModel.getArticlesByCategory("business")
        viewModel.onSelectedCategoryChanged("business")

        Categories(viewModel = viewModel, onFetchCategory = {
            viewModel.onSelectedCategoryChanged(it)
            viewModel.getArticlesByCategory(it)
        }, isLoading = isLoading, isError = isError)
    }
    composable(BottomMenuScreen.Sources.route){
        Sources(viewModel = viewModel, isLoading = isLoading, isError= isError,)
    }
}