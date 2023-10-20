package com.rangga.storyapp.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rangga.storyapp.data.parcel.LoginParcel
import com.rangga.storyapp.data.parcel.RegistParcel
import com.rangga.storyapp.data.response.LoginResponse
import com.rangga.storyapp.data.response.RegisterResponse
import com.rangga.storyapp.data.retrofit.ApiRequest
import com.rangga.storyapp.helper.TokenDatastore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.viewModelScope
import com.rangga.storyapp.data.retrofit.AuthInterceptor
import com.rangga.storyapp.helper.SessionManager
import kotlinx.coroutines.launch

class MainViewModel(private val context: Context) : ViewModel() {
    val sessionManager = SessionManager(context)
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val showToastMessage: MutableLiveData<Boolean> = MutableLiveData()
    val token: MutableLiveData<String> = MutableLiveData()

    fun clearToken() {
        sessionManager.clearAuthToken()
    }

    fun login(data: LoginParcel) {
        loading.value = true

        val call = ApiRequest.getApiService(context).login(data)

        call.enqueue(object : Callback<LoginResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    loading.value = false
                    showToastMessage.value = true
                    saveToken(response.body()?.loginResult?.token.toString())
                    token.value = response.body()?.loginResult?.token.toString()
                } else {
                    loading.value = false
                    showToastMessage.value = false
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            }
        })
    }

    fun getToken() {
        token.value = sessionManager.fetchAuthToken()
    }

    fun saveToken(data: String) {
        viewModelScope.launch {
            sessionManager.saveAuthToken(data)
        }
    }
}