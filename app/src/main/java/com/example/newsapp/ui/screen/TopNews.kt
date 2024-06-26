package com.example.newsapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.R
import com.example.newsapp.components.ErrorUI
import com.example.newsapp.components.LoadingUI
import com.example.newsapp.components.SearchBar
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.ui.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TopNews(navController: NavController,articles: List<TopNewsArticle>,query:MutableState<String>, viewModel: MainViewModel, isLoading:MutableState<Boolean>, isError: MutableState<Boolean> ){
    Column(modifier= Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        SearchBar(query=query,viewModel)
        val searchedText = query.value
        val resultList = mutableListOf<TopNewsArticle>()
        resultList.clear()
        if (searchedText != "") {
            resultList.addAll(viewModel.searchedNewsResponse.collectAsState().value.articles?:articles)
        }else {
            resultList.addAll(articles)
        }

        when {
            isLoading.value -> {
                LoadingUI()
            }
            isError.value -> {
                ErrorUI()
            }else -> {
                LazyColumn {
                    items(resultList.size) {
                            index ->
                                    TopNewsItem( article=resultList[index],
                                        onNewsClick = {
                                       navController.navigate("Detail/$index")
                                   })
                    }
                }
            }
        }
    }
}

@Composable
fun TopNewsItem(article: TopNewsArticle, onNewsClick: ()-> Unit = {},){
    Box(modifier= Modifier
        .height(200.dp)
        .padding(8.dp)
        .background(color = colorResource(id = R.color.purple_700))
        .clickable {
            onNewsClick()
        }) {
        CoilImage(
            imageModel = article.urlToImage,
            contentScale =ContentScale.Crop,
            error = ImageBitmap.imageResource(id = R.drawable.breaking_news),
            placeHolder = ImageBitmap.imageResource(id = R.drawable.breaking_news),
        )
        Column(modifier= Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = MockData.stringToDate(article.publishedAt?:"2024-04-12T09:52:25").getTimeAgo(), color= Color.White, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(80.dp))
            Text(text = article.title?:"Title not Available", color = Color.White, fontWeight = FontWeight.SemiBold,
                maxLines=2,overflow = TextOverflow.Ellipsis)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview(){
    TopNewsItem(TopNewsArticle(
        author="Héctor",
        title="Great day on Wallstreet",
        description = "Many people happy on Wallstreet",
        publishedAt = "2022-06-23T12:23:37:00Z"
    ))
}