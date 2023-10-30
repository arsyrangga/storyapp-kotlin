package com.rangga.storyapp.data.paging

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rangga.storyapp.data.parcel.ListStoryParcel
import com.rangga.storyapp.data.retrofit.ApiService
import retrofit2.awaitResponse

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, ListStoryParcel>() {
    companion object {
        const val INITIAL_PAGE_INDEX = 1
        fun snapshot(items: List<ListStoryParcel>): PagingData<ListStoryParcel> {
            return PagingData.from(items)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryParcel> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getStories(position, 10).awaitResponse().body()
            val response = responseData?.listStory?.map {
                ListStoryParcel(
                id = it?.id.toString(),
                name = it?.name,
                description = it?.description,
                photoUrl = it?.photoUrl,
                createdAt = it?.createdAt,
                lat = it?.lat,
                lon = it?.lon,)
            }

                LoadResult.Page(
                    data =  response ?: emptyList(),
                    prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                    nextKey = if (response.isNullOrEmpty()) null else position + 1
                )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryParcel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}