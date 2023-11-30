package mohammad.toriq.mynews.presentation.screen.sources

import mohammad.toriq.mynews.domain.model.Source

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
data class SourcesUiState (
    val sourcesList: ArrayList<Source> = ArrayList(),
    val isLoading: Boolean = false
)