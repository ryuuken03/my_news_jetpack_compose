package mohammad.toriq.mynews.presentation.screen.sources

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mohammad.toriq.mynews.data.source.remote.Resource
import mohammad.toriq.mynews.domain.model.Source
import mohammad.toriq.mynews.domain.usecase.GetSourcesUseCase
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
@HiltViewModel
class SourcesViewModel @Inject constructor(
    private val getSourcesUseCase: GetSourcesUseCase
) : ViewModel()  {

    private val _state = mutableStateOf(SourcesUiState())
    val state: State<SourcesUiState> = _state

    var showSearch = false
    var search:String? = null
    var isInit = false
    var sources = ArrayList<Source>()

    fun searching(){
        viewModelScope.launch {
            var list = ArrayList<Source>()
            sources.forEach { source ->
                var isAdd = true
                if(search != null){
                    if(!search.equals("")){
                        isAdd = false
                        if(source.name!!.lowercase().contains(search!!.lowercase())){
                            isAdd = true
                        }
                    }
                }
                if(isAdd){
                    list.add(source)
                }
            }
            _state.value = state.value.copy(
                sourcesList = list,
                isLoading = false
            )
        }
    }

    fun getSources(category :String) {
        showSearch = false
        sources = ArrayList()
        viewModelScope.launch {
            getSourcesUseCase(category).onEach { result ->
                var list = ArrayList<Source>()
                when (result) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            sourcesList = list,
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        showSearch = true
                        result.data!!.sources.forEach {
                            sources.add(it)
                            list.add(it)
                        }
                        _state.value = state.value.copy(
                            sourcesList = list,
                            isLoading = false
                        )
                        showSearch = true
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            sourcesList = list,
                            isLoading = false
                        )

                    }
                }
            }.launchIn(this)
        }
    }
}