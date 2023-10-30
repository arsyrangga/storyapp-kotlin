package com.rangga.storyapp.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rangga.storyapp.data.response.ListStoryResponse
import com.rangga.storyapp.data.retrofit.ApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.awaitResponse

class MapViewModel(private val context: Context): ViewModel() {
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val dataResponse: MutableLiveData<ListStoryResponse?> = MutableLiveData()
    fun getData() {
        loading.value = true


        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiRequest.getApiService(context).getStories(1).awaitResponse()
                if(response.isSuccessful){
                    val data = response.body()
                    dataResponse.postValue(data)
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
            }
        }}
}