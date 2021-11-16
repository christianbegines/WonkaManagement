package es.christianbegines.wonkamanagement.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import es.christianbegines.wonkamanagement.models.FilterOompaLoompa
import es.christianbegines.wonkamanagement.models.OompaLoompa
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OompaLoompaRepository @Inject constructor(private val service:AppService) {
    fun getOompaLoompaStreamResult(query: FilterOompaLoompa): Flow<PagingData<OompaLoompa>>{
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { OompaLoompaPagingSource(query,service) }
        ).flow
    }

    suspend fun getOompaLompa(id:String):OompaLoompa{
        return service.getOompaLoompa(id)
    }
    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }
}