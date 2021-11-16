package es.christianbegines.wonkamanagement.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.christianbegines.wonkamanagement.R
import es.christianbegines.wonkamanagement.models.OompaLoompa
import es.christianbegines.wonkamanagement.remote.OompaLoompaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: OompaLoompaRepository
): ViewModel()
{
    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    val state: StateFlow<State>
        get() = _state

    fun getOompaLoompa(id:String) {
        viewModelScope.launch {
            _state.value = State.Loading
            try {
                val response = repository.getOompaLompa(id)
                _state.value = State.Success(response)
            } catch (e: Exception) {
                _state.value = State.Error(R.string.unknownError)
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val oompaLoompa: OompaLoompa) : State()
        data class Error(val message: Int) : State()
    }
}