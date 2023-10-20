package com.rangga.storyapp.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rangga.storyapp.data.parcel.RegistParcel
import com.rangga.storyapp.data.response.ListStoryResponse
import com.rangga.storyapp.data.response.RegisterResponse
import com.rangga.storyapp.data.retrofit.ApiRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val context: Context) : ViewModel() {
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val showToastMessage: MutableLiveData<Boolean> = MutableLiveData()

    fun Register(data: RegistParcel) {
        loading.value = true

        val call = ApiRequest.getApiService(context).register(data)

        call.enqueue(object : Callback<RegisterResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<RegisterResponse>, response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    loading.value = false
                    showToastMessage.value = true
                } else {
                    loading.value = false
                    showToastMessage.value = false
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
            }
        })
    }
}