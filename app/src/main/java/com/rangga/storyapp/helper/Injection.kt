package com.rangga.storyapp.helper

import android.content.Context
import com.rangga.storyapp.data.database.StoryDatabase
import com.rangga.storyapp.data.repository.StoryRepository
import com.rangga.storyapp.data.retrofit.ApiRequest

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiRequest.getApiService(context)
        return StoryRepository(database, apiService)
    }
}