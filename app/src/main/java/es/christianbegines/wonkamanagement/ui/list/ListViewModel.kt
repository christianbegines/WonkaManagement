package es.christianbegines.wonkamanagement.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import es.christianbegines.wonkamanagement.R
import es.christianbegines.wonkamanagement.models.FilterOompaLoompa
import es.christianbegines.wonkamanagement.models.OompaLoompa
import es.christianbegines.wonkamanagement.remote.OompaLoompaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: OompaLoompaRepository
) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State>
        get() = _state
    private var currentOompaLoompa: Flow<PagingData<OompaLoompa>>? = null
    fun getOompaLoompas(query: FilterOompaLoompa): Flow<PagingData<OompaLoompa>> {
        _state.value = State.Loading
        try{
            val result = repository.getOompaLoompaStreamResult(query).cachedIn(viewModelScope)
            currentOompaLoompa = result
        }catch (e:Exception){
            _state.value = State.Error(R.string.unknownError)
        }
        _state.value = State.Success
        return currentOompaLoompa!!
    }

    sealed class State {
        object Loading : State()
        object Success : State()
        data class Error(val message: Int) : State()
    }
}