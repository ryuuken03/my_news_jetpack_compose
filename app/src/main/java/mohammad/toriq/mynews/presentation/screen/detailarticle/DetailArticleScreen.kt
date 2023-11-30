@file:OptIn(ExperimentalMaterial3Api::class)

package mohammad.toriq.mynews.presentation.screen.detailarticle

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import mohammad.toriq.mynews.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

/***
 * Created By Mohammad Toriq on 14/11/2023
 */
@Composable
fun DetailArticleScreen(
    activity: ComponentActivity,
    navController: NavController,
    url_title :String
){
    var split = url_title.split("&")
    var url = url_title
    var title = "Detail Article"
    if(split.size > 1){
        url = split[0]
        title = split[1]
    }
    initDetailArticle(navController = navController, url = url, title = title)
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun initDetailArticle(
    navController: NavController,
    url :String,
    title :String
){
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Surface(
                shadowElevation = 3.dp
            ){
                TopAppBar(
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = colorResource(id = R.color.white),
                        titleContentColor = colorResource(id = R.color.black),
                    ),
                    title = {
                        Text(
                            text = title,
                            maxLines = 1,
                            fontSize = 16.sp,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold
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
                    }
                )
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()) {
                AndroidView(
                    modifier = Modifier,
                    factory = { context ->
                        WebView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            webViewClient = object : WebViewClient() {
                                override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                                    backEnabled = view.canGoBack()
                                }
                            }
                            settings.javaScriptEnabled = true
                            loadUrl(url)
                            webView = this
                        }
                    }, update = {
                        webView = it
                    })
                BackHandler(enabled = backEnabled) {
                    webView?.goBack()
                }
            }
        }
    )
}