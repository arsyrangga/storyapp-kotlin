package com.rangga.storyapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rangga.storyapp.data.parcel.ListStoryParcel
import com.rangga.storyapp.data.repository.StoryRepository

class HomeViewModel(storyRepository: StoryRepository) : ViewModel() {
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val list: LiveData<PagingData<ListStoryParcel>> =
        storyRepository.getStory().cachedIn(viewModelScope)
}