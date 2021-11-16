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
            val oompaLoompas = filter(filterOompaLoompa, response.results)
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
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    private fun filter(filter: FilterOompaLoompa, list: List<OompaLoompa>): List<OompaLoompa> {
        var oompaLoompas: List<OompaLoompa> = list
        filter.gender?.let {
            oompaLoompas= oompaLoompas.filter{
                it.gender.lowercase().contains(filter.gender!!.lowercase())
            }
        }
        filter.name?.let{
            oompaLoompas = oompaLoompas.filter {
                it.firstName.lowercase().contains(filter.name!!.lowercase())
            }
        }
        filter.profession?.let{
            oompaLoompas = oompaLoompas.filter {
                it.profession.lowercase().contains(filter.profession!!.lowercase())
            }
        }
        return oompaLoompas
    }
}