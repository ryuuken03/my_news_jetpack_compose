package mohammad.toriq.mynews.presentation.ui.navigation

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object CategoriesScreen: Screen("categories_screen")
    object SourcesScreen: Screen("sources_screen?{category}"){
        fun sendData(category: String) = "sources_screen?$category"
    }
    object ArticlesScreen: Screen("articles_screen/{source}/{title}"){
        fun sendData(source: String,title: String) = "articles_screen/$source/$title"
    }
    object DetailArticleScreen: Screen("detail_article_screen?{url_title}"){
        fun sendData(url_title: String) = "detail_article_screen?$url_title"
    }
}