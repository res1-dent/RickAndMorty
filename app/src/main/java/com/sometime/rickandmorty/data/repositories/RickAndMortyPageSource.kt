package com.sometime.rickandmorty.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sometime.rickandmorty.data.mappers.RemoteMapper
import com.sometime.rickandmorty.data.network.RickAndMortyApi
import com.sometime.rickandmorty.domain.entities.Person
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import timber.log.Timber

class RickAndMortyPageSource @AssistedInject constructor(
    private val api: RickAndMortyApi,
    private val mapper: RemoteMapper,
    @Assisted ("query") private val query: String?
) : PagingSource<Int, Person>() {

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        return try {
            val page: Int = params.key ?: 1
            val pageSize = params.loadSize.coerceAtMost(20)
            val response = api.getCharacters(page = if (page == 1) null else page, name = query)
            if (response.isSuccessful) {
                val persons =
                    checkNotNull(response.body()).results.let { mapper.toPersonList(it) }
                val nextKey: Int? =
                    response.body()?.info?.next?.takeLastWhile { it != '=' }?.toIntOrNull()
                //if (persons.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(persons, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            Timber.tag("Exception").e(e)
            LoadResult.Error(e)
        }

    }
    @AssistedFactory
    interface RickAndMortyPageSourceFactory {
        fun create(@Assisted("query") query: String): RickAndMortyPageSource
    }
}


