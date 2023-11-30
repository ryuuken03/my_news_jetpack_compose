package mohammad.toriq.mynews.presentation.screen.categories

import mohammad.toriq.mynews.domain.model.Article

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
data class CategoryUiState (
    val categoryList: ArrayList<String> = ArrayList(),
    val isLoading: Boolean = false
)