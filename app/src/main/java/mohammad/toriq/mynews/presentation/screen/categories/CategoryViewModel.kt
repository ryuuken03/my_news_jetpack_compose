package mohammad.toriq.mynews.presentation.screen.categories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mohammad.toriq.mynews.util.Constants
import javax.inject.Inject

/***
 * Created By Mohammad Toriq on 29/11/2023
 */
@HiltViewModel
class CategoryViewModel @Inject constructor(
) : ViewModel()  {

    private val _state = mutableStateOf(CategoryUiState())
    val state: State<CategoryUiState> = _state

    init {
        loading()
    }

    fun getCategories() {
        _state.value = state.value.copy(
            categoryList = Constants.getCategories(),
            isLoading = false
        )
    }

    fun loading() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                categoryList = ArrayList(),
                isLoading = true
            )
            delay(500)
            getCategories()

        }
    }
}