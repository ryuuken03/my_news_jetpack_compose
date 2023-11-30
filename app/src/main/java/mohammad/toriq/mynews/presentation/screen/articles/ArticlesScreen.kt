@file:OptIn(ExperimentalMaterial3Api::class)

package mohammad.toriq.mynews.presentation.screen.articles

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import mohammad.toriq.mynews.R
import mohammad.toriq.mynews.presentation.ui.navigation.Screen
import mohammad.toriq.mynews.util.Constants
import mohammad.toriq.mynews.util.Util

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
@Composable
fun ArticlesScreen (
    activity: ComponentActivity,
    navController: NavController,
    sources :String,
    title :String,
    articlesViewModel: ArticlesViewModel = hiltViewModel(),
){

    if(!articlesViewModel.isInit){
        articlesViewModel.isInit = true
        articlesViewModel.sources = sources
        articlesViewModel.getArticles()
    }
    initArticles(
        activity,
        navController,
        title,
        articlesViewModel,)
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun initArticles(
    activity: ComponentActivity,
    navController: NavController,
    title: String,
    articlesViewModel: ArticlesViewModel,
    modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Surface(
                shadowElevation = 3.dp
            ){
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = colorResource(id = R.color.white),
                        titleContentColor = colorResource(id = R.color.black),
                    ),
                    title = {
                        Text(
                            text = title,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigateUp()
                            }) {
                            Image(
                                imageVector = Icons.Filled.ArrowBack,
                                colorFilter = ColorFilter.tint(colorResource(id = R.color.black)),
                                contentDescription = "Back"
                            )
                        }
                    },
                )
            }
        },
        content = {
            var isLoading = articlesViewModel.state.value.isLoading
            var articles = articlesViewModel.state.value.articlesList
            var isRefreshing = articlesViewModel.isRefresh
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(5.dp)
                    .fillMaxSize()) {

                if(!isLoading || articles.size > 0){
                    val listState = rememberLazyListState()

                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                        onRefresh = {
                            articlesViewModel.refresh()
                        }
                    ) {
                        LazyColumn(
                            state = listState
                        ) {
                            item {
                                var text by remember { mutableStateOf(TextFieldValue(articlesViewModel.search?:"")) }
                                val customTextSelectionColors = TextSelectionColors(
                                    handleColor = colorResource(id = R.color.blue),
                                    backgroundColor = colorResource(id = R.color.blue2)
                                )
                                CompositionLocalProvider (
                                    LocalTextSelectionColors provides customTextSelectionColors,
                                ){
                                    BasicTextField(
                                        value = text,
                                        onValueChange = { newText ->
                                            text = newText
                                            if(newText.text.length == 0){
                                                articlesViewModel.search = null
                                                articlesViewModel.refresh()
                                            }
                                        },
                                        singleLine = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                color = colorResource(id = R.color.white),
                                            ),
                                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                        keyboardActions = KeyboardActions(
                                            onSearch = {
                                                articlesViewModel.search = text.text
                                                articlesViewModel.refresh()
                                                keyboardController?.hide()
                                            }
                                        ),
                                        decorationBox = { innerTextField ->
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(all = 5.dp)
                                                    .border(
                                                        width = 1.dp,
                                                        color = colorResource(id = R.color.black),
                                                        shape = RoundedCornerShape(6.dp)
                                                    )

                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(10.dp),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Icon(
                                                        imageVector = Icons.Default.Search,
                                                        contentDescription = "search",
                                                    )
                                                    Box(modifier = Modifier
                                                        .weight(1f)
                                                        .padding(horizontal = 5.dp)){
                                                        if(text.text.isEmpty())
                                                            Text(text = "Searching",
                                                                color = colorResource(id = R.color.grey),
                                                                fontSize = 14.sp,)
                                                        innerTextField()
                                                    }
                                                    if(text.text.length > 0){
                                                        Icon(
                                                            imageVector = Icons.Default.Close,
                                                            contentDescription = "closeIcon",
                                                            modifier = Modifier.clickable {
                                                                var reset by mutableStateOf(TextFieldValue(""))
                                                                text = reset
                                                                articlesViewModel.search = null
                                                                articlesViewModel.refresh()
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                            items(articles.size) { index ->

                                OutlinedButton(
                                    modifier = Modifier.padding(all = 5.dp),
                                    contentPadding = PaddingValues(),
                                    border = BorderStroke(1.dp, colorResource(id = R.color.black)),
                                    shape = RoundedCornerShape(2),
                                    onClick = fun(){
                                    }
                                ){
                                    var article = articles[index]
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(
                                                onClick = fun() {
                                                    navController.navigate(
                                                        Screen.DetailArticleScreen
                                                            .sendData(article.url!! + "&" + article.title!!)
                                                    )
                                                }
                                            ),
                                    ){
                                        if(article.urlToImage!=null){
                                            AsyncImage(
                                                model = article.urlToImage,
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(200.dp)
                                            )
                                        }
                                        if(article.publishedAt!=null){
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(
                                                text = "Publish : "+changeFormatDate(article.publishedAt!!),
                                                fontSize = 11.sp,
                                                color = colorResource(id = R.color.grey),
                                                modifier = Modifier
                                                    .padding(horizontal = 10.dp)
                                                    .align(Alignment.End),
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = article.title!!,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = colorResource(id = R.color.black),
                                            modifier = Modifier
                                                .padding(horizontal = 5.dp),
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = article.description?: article.content?: "",
                                            fontSize = 12.sp,
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis,
                                            color = colorResource(id = R.color.black),
                                            modifier = Modifier
                                                .padding(horizontal = 10.dp)
                                                .align(Alignment.End),
                                        )
                                        if(article.author!=null){
                                            Spacer(modifier = Modifier.height(15.dp))
                                            Text(
                                                text = "Author : "+article.author,
                                                fontSize = 12.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                color = colorResource(id = R.color.grey),
                                                fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier
                                                    .padding(horizontal = 10.dp),
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }
                                }
                            }
                            if(articles.size == 0){
                                item{
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(400.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Text(
                                            text = "Article not Found",
                                            color = colorResource(id = R.color.grey),
                                            fontWeight = FontWeight.SemiBold,
                                            textAlign = TextAlign.Center,
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                        listState.OnBottomReached (buffer = 2){
                            if(!articlesViewModel.isMax){
                                articlesViewModel.loadMore()
                            }
                        }
                    }
                }
                if(isLoading && articlesViewModel.page == 1){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = colorResource(id = R.color.black)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun LazyListState.OnBottomReached(
    buffer : Int = 0,
    loadMore : () -> Unit
){
    require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                return@derivedStateOf true
            lastVisibleItem.index >=  layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(shouldLoadMore){
        snapshotFlow { shouldLoadMore.value }
            .collect { if (it) loadMore() }
    }
}

fun changeFormatDate(publishedAt: String) : String{
    var inFormat = Constants.DATE_OUT_FORMAT_DEF0
    if(publishedAt.contains(".")){
        inFormat = Constants.DATE_OUT_FORMAT_DEF1
    }
    return Util.convertDate(publishedAt, inFormat, Constants.DATE_OUT_FORMAT_DEF3)
}