package es.christianbegines.wonkamanagement.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import es.christianbegines.wonkamanagement.models.FilterOompaLoompa
import es.christianbegines.wonkamanagement.models.OompaLoompa
import es.christianbegines.wonkamanagement.remote.OompaLoompaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: OompaLoompaRepository
    ): ViewModel()
{
        private var currentOompaLoompa:Flow<PagingData<OompaLoompa>>? = null

        fun getOompaLoompas(query:FilterOompaLoompa):Flow<PagingData<OompaLoompa>>{
            val result = repository.getOompaLoompaStreamResult(query).cachedIn(viewModelScope)
            currentOompaLoompa = result
            return result
        }
}