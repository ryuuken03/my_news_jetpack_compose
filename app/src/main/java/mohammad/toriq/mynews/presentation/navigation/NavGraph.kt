package mohammad.toriq.mynews.presentation.ui.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import mohammad.toriq.mynews.presentation.screen.articles.ArticlesScreen
import mohammad.toriq.mynews.presentation.screen.categories.CategoriesScreen
import mohammad.toriq.mynews.presentation.screen.detailarticle.DetailArticleScreen
import mohammad.toriq.mynews.presentation.screen.sources.SourcesScreen
import mohammad.toriq.mynews.presentation.ui.screen.SplashScreen

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
@Composable
fun NavGraph(activity: ComponentActivity, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(
                activity = activity,
                navController = navController
            )
        }
        composable(route = Screen.CategoriesScreen.route) {
            CategoriesScreen(
                activity = activity,
                navController = navController
            )
        }
        composable(route = Screen.SourcesScreen.route){
            var category = it.arguments?.getString("category") ?: ""
            SourcesScreen(activity,navController,category)
        }
        composable(route = Screen.ArticlesScreen.route){
            var source = it.arguments?.getString("source") ?: ""
            var title = it.arguments?.getString("title") ?: ""
            ArticlesScreen(activity,navController,source,title)
        }
        composable(route = Screen.DetailArticleScreen.route){
            var url_title = it.arguments?.getString("url_title") ?: ""
            DetailArticleScreen(activity,navController,url_title)
        }
    }
}