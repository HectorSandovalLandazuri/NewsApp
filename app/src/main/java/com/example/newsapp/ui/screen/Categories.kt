package com.example.newsapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.R
import com.example.newsapp.components.ErrorUI
import com.example.newsapp.components.LoadingUI
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.models.getAllArticleCategory
import com.example.newsapp.ui.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun Categories(onFetchCategory: (String) -> Unit={}, viewModel: MainViewModel, isLoading:MutableState<Boolean>, isError:MutableState<Boolean>){
    val tabItems= getAllArticleCategory()

    when {
        isLoading.value -> {
            LoadingUI()
        }
        isError.value -> {
            ErrorUI()
        }
        else -> {
            Column {
                LazyRow {
                    items(tabItems.size){
                        val category=tabItems[it]
                        CategoryTab(category = category.categoryName, onFetchCategory = onFetchCategory,
                            isSelected = viewModel.selectedCategory.collectAsState().value==category)
                    }
                }
                ArticleContent(articles = viewModel.getArticleByCategory.collectAsState().value.articles ?: listOf())
            }
        }
    }
}






@Composable
fun CategoryTab(category:String,isSelected:Boolean=false,
                onFetchCategory: (String)-> Unit){
    val background=if(isSelected) colorResource(id= R.color.purple_200)
        else colorResource(id= R.color.purple_700)
    colorResource(id= R.color.purple_200)
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable {
                onFetchCategory(category)
            },
        shape= MaterialTheme.shapes.small,
        color=background
    ) {
        Text(text=category,
            style=MaterialTheme.typography.body2,
            color= Color.White,
            modifier = Modifier.padding(start=8.dp))
    }
}

@Composable
fun ArticleContent(articles:List<TopNewsArticle>,modifier: Modifier=Modifier){
    LazyColumn {
        items(articles){
            article ->
                Card(modifier = Modifier.padding(8.dp), 
                    border = BorderStroke(2.dp, color= colorResource(id = R.color.purple_500))) {
                    Row(
                        modifier
                            .fillMaxWidth()
                            .padding(8.dp)) {
                        CoilImage(
                            imageModel = article.urlToImage,
                            modifier=Modifier.size(100.dp),
                            placeHolder = painterResource(id = R.drawable.breaking_news),
                            error = painterResource(id = R.drawable.breaking_news),
                        )
                        Column(modifier.padding(8.dp)) {
                            Text(text=article.title?:"Not Available",
                                fontWeight = FontWeight.Bold,
                                maxLines=3, overflow = TextOverflow.Ellipsis)
                            Row(
                                modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(text=article.author?:"Not Available")
                                Text(text=MockData.stringToDate(article.publishedAt?:"2022-06-28T14:22:07Z").getTimeAgo())

                            }

                        }
                    }
                }
        }
    }
}

@Preview
@Composable
fun ArticleContentPreview(){
    ArticleContent(articles= listOf(TopNewsArticle(
        author="Héctor",
        title="Great day on Wallstreet",
        description = "Many people happy on Wallstreet",
        publishedAt = "2022-06-23T12:23:37:00Z"
    )))
}
