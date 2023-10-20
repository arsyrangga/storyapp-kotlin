package com.rangga.storyapp.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rangga.storyapp.data.parcel.LoginParcel
import com.rangga.storyapp.data.response.ListStoryDataResponse
import com.rangga.storyapp.data.response.ListStoryResponse
import com.rangga.storyapp.data.response.LoginResponse
import com.rangga.storyapp.data.retrofit.ApiRequest
import com.rangga.storyapp.helper.SessionManager
import com.rangga.storyapp.helper.TokenDatastore
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val context: Context) : ViewModel() {
    val sessionManager = SessionManager(context)
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val list: MutableLiveData<List<ListStoryDataResponse>> = MutableLiveData()


    fun removeToken() {
        viewModelScope.launch {
            sessionManager.clearAuthToken()
        }
    }

    fun getData() {
        loading.value = true
        val call = ApiRequest.getApiService(context).getStories()

        call.enqueue(object : Callback<ListStoryResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ListStoryResponse>, response: Response<ListStoryResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    val dataList = mutableListOf<ListStoryDataResponse>()
                    data.let {
                        it!!.listStory!!.map {
                            dataList.add(ListStoryDataResponse(name = it?.name!!, description =  it?.description!!, id = it?.id!!, photoUrl = it?.photoUrl!!))
                        }
                    }
                    list.value = dataList
                }
            }

            override fun onFailure(call: Call<ListStoryResponse>, t: Throwable) {
            }
        })
    }


}