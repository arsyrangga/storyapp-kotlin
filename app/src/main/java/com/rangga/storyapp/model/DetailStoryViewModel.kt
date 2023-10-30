package com.rangga.storyapp.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rangga.storyapp.data.response.DetailStoryResponse
import com.rangga.storyapp.data.response.FileUploadResponse
import com.rangga.storyapp.data.retrofit.ApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.awaitResponse

class DetailStoryViewModel(private val context: Context) : ViewModel() {
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val dataResponse: MutableLiveData<DetailStoryResponse?> = MutableLiveData()
    fun getDetail(id:String) {
        loading.value = true


        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiRequest.getApiService(context).getDetailStories(id).awaitResponse()
                if(response.isSuccessful){
                    val data = response.body()
                    dataResponse.postValue(data)
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
            }
        }}
}