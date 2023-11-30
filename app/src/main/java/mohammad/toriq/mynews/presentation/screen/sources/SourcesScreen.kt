@file:OptIn(ExperimentalMaterial3Api::class)

package mohammad.toriq.mynews.presentation.screen.sources

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mohammad.toriq.mynews.R
import mohammad.toriq.mynews.presentation.ui.navigation.Screen

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
@Composable
fun SourcesScreen (
    activity: ComponentActivity,
    navController: NavController,
    category :String,
    sourcesViewModel: SourcesViewModel = hiltViewModel(),
){

    var state = sourcesViewModel.state.value
    if(!sourcesViewModel.isInit){
        sourcesViewModel.isInit = true
        sourcesViewModel.getSources(category.lowercase())
    }
    initSources(
        activity,
        navController,
        category,
        sourcesViewModel,)
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun initSources(
    activity: ComponentActivity,
    navController: NavController,
    category: String,
    sourcesViewModel: SourcesViewModel,
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
                            text = category,
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
            var isLoading = sourcesViewModel.state.value.isLoading
            var sources = sourcesViewModel.state.value.sourcesList
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(5.dp)
                    .fillMaxSize()) {

                if(!isLoading){
                    LazyColumn(
                    ) {
                        item {
                            var text by remember { mutableStateOf(TextFieldValue(sourcesViewModel.search?:"")) }
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
                                            sourcesViewModel.search = null
                                            sourcesViewModel.searching()
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
                                            sourcesViewModel.search = text.text
                                            sourcesViewModel.searching()
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
                                                            sourcesViewModel.search = null
                                                            sourcesViewModel.searching()
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
                        items(sources.size) { index ->
                            OutlinedButton(
                                modifier = Modifier.padding(all = 5.dp),
                                border = BorderStroke(1.dp, colorResource(id = R.color.black)),
                                shape = RoundedCornerShape(4),
                                onClick = fun(){
//                                    Toast.makeText(activity,sources[index].name!!,Toast.LENGTH_SHORT).show()
                                    navController.navigate(
                                        Screen.ArticlesScreen
                                            .sendData(sources[index].id!!, sources[index].name!!)
                                    )
                                }
                            ){
                                Row (
//                                    horizontalAlignment  = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(
                                        text = sources[index].name!!,
                                        fontSize = 14.sp,
                                        color = colorResource(id = R.color.black),
                                        textAlign = TextAlign.Center,
                                    )
                                    Icon(
                                        Icons.Filled.ArrowForward,
                                        modifier = Modifier.size(20.dp),
                                        tint = colorResource(id = R.color.black),
                                        contentDescription = "",
                                    )
                                }
                            }
                        }
                        if(sources.size == 0){
                            item{
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Text(
                                        text = "Source not Found",
                                        color = colorResource(id = R.color.grey),
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }else{
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
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

//@Preview
//@Composable
//fun prevCat(){
//    initSources(Constants.getCategories(),false)
//}