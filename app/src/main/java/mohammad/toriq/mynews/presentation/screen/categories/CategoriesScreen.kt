@file:OptIn(ExperimentalMaterial3Api::class)

package mohammad.toriq.mynews.presentation.screen.categories

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mohammad.toriq.mynews.R
import mohammad.toriq.mynews.presentation.ui.navigation.Screen
import mohammad.toriq.mynews.util.Constants

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
@Composable
fun CategoriesScreen (
    activity: ComponentActivity,
    navController: NavController,
    categoryViewModel: CategoryViewModel = hiltViewModel()
){
    var state = categoryViewModel.state.value
    initCategories(
        activity,
        navController,
        state.categoryList,
        state.isLoading)
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun initCategories(
    activity: ComponentActivity,
    navController: NavController,
    categories : ArrayList<String>,
    isLoading : Boolean,
    modifier: Modifier = Modifier) {
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
                            text = "Categories News",
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                )
            }
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .padding(5.dp)
                    .fillMaxSize()) {

                if(!isLoading && categories.size > 0){
                    LazyColumn(
                    ) {
                        var icons = Constants.getCategoryIcons()
                        items(categories.size) { index ->
                            OutlinedButton(
                                modifier = Modifier.padding(all = 5.dp),
                                border = BorderStroke(1.dp, colorResource(id = R.color.black)),
                                shape = RoundedCornerShape(4),
                                onClick = fun(){
//                                    Toast.makeText(activity,categories[index],Toast.LENGTH_SHORT).show()
                                    navController.navigate(
                                        Screen.SourcesScreen
                                            .sendData(categories[index])
                                    )
                                }
                            ){
                                Row (
                                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ){
                                    Icon(
                                        modifier = Modifier.size(30.dp),
                                        painter = painterResource(icons[index]),
                                        tint = colorResource(id = R.color.black),
                                        contentDescription = "",
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = categories[index],
                                        fontSize = 14.sp,
                                        color = colorResource(id = R.color.black),
                                        textAlign = TextAlign.Center,
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