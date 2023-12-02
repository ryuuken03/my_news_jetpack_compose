package mohammad.toriq.mynews.presentation.screen.articles

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mohammad.toriq.mynews.domain.usecase.GetArticlesUseCase
import mohammad.toriq.mynews.data.source.remote.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel()  {

    private val _state = mutableStateOf(ArticlesUiState())
    val state: State<ArticlesUiState> = _state

    var page = 1
    var showSearch = false
    var search : String? = null
    var remainingArticle = 0
    var isRefresh = false
    var isMax = false
    var isInit = false
    var sources = ""

    fun getArticles() {
        viewModelScope.launch {
            getArticlesUseCase(search,sources,page).onEach { result ->
                var list = state.value.articlesList
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            articlesList = list,
//                            isLoading = if (page == 1) true else false
                            isLoading = true,
                            errorMessage = ""
                        )
                    }
                    is Resource.Success -> {
                        if(page == 1){
                            isInit = true
                            remainingArticle = result.data?.totalResults!!
                        }
                        if(result.data?.articles !=null){
                            result.data.articles.forEach {
                                list.add(it)
                            }
                            remainingArticle = remainingArticle - result.data.articles.size
                        }
                        if(remainingArticle > 0){
                            page++
                        }else{
                            isMax = true
                        }
                        _state.value = state.value.copy(
                            articlesList = list,
                            isLoading = false,
                            errorMessage = ""
                        )
                        showSearch = true
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            articlesList = list,
                            isLoading = false,
                            errorMessage = "Failed Connected To API Server"
                        )
                        isMax = true
                        showSearch = true

                    }
                }
            }.launchIn(this)
        }
    }

    fun loadMore(){
        Log.d("OkCheck","loadMore:"+page.toString())
        getArticles()
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("OkCheck","refresh")
            isRefresh = true
            isMax = false
            showSearch = false
            page = 1
            _state.value = state.value.copy(
                articlesList = ArrayList(),
                isLoading = false,
                errorMessage = ""
            )
            remainingArticle = 0
            getArticles()
            isRefresh = false
        }

    }
}