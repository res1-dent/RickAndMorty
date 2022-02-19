package com.sometime.rickandmorty.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sometime.rickandmorty.data.mappers.RemoteMapper
import com.sometime.rickandmorty.domain.entities.Person
import retrofit2.HttpException
import javax.inject.Inject

class RickAndMortyPageSource @Inject constructor(
    private val api: RickAndMortyApi,
    private val mapper: RemoteMapper
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
            val response = api.getCharacters(page = if (page == 1) null else page)
            if (response.isSuccessful) {
                val persons =
                    checkNotNull(response.body()).results.let { mapper.toPersonList(it) }
                val nextKey = if (persons.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(persons, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

}