package mohammad.toriq.mynews.presentation.screen.articles

import mohammad.toriq.mynews.domain.model.Article

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
data class ArticlesUiState (
    val articlesList: ArrayList<Article> = ArrayList(),
    val isLoading: Boolean = false,
    val errorMessage : String = ""
)