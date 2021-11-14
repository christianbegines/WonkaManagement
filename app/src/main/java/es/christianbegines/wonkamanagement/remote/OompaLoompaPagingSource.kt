package es.christianbegines.wonkamanagement.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import es.christianbegines.wonkamanagement.models.FilterOompaLoompa
import es.christianbegines.wonkamanagement.models.OompaLoompa

private const val UNSPLASH_STARTING_PAGE_INDEX = 1


class OompaLoompaPagingSource(
    private val filterOompaLoompa: FilterOompaLoompa,
    private val service: AppService
) : PagingSource<Int, OompaLoompa>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OompaLoompa> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = service.getOompaLoompas(page)
            var oompaLoompas = response.results
            if(filterOompaLoompa.name != null){
                oompaLoompas = oompaLoompas.filter {
                    it.firstName.lowercase().contains(filterOompaLoompa.name!!)
                }
            }
            if(filterOompaLoompa.gender != null){
                oompaLoompas = oompaLoompas.filter {
                    it.gender.contains(filterOompaLoompa.gender!!)
                }
            }

            LoadResult.Page(
                data = oompaLoompas.toList(),
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == 20) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, OompaLoompa>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}