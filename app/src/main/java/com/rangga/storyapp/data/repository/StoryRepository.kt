package com.rangga.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rangga.storyapp.data.database.StoryDatabase
import com.rangga.storyapp.data.paging.StoryPagingSource
import com.rangga.storyapp.data.parcel.ListStoryParcel
import com.rangga.storyapp.data.retrofit.ApiService

class StoryRepository(private val quoteDatabase: StoryDatabase, private val apiService: ApiService) {
    fun getStory() : LiveData<PagingData<ListStoryParcel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false,

                ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }
}