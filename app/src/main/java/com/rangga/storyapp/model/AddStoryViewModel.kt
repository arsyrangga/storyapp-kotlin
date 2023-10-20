package com.rangga.storyapp.model

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rangga.storyapp.data.parcel.LoginParcel
import com.rangga.storyapp.data.response.FileUploadResponse
import com.rangga.storyapp.data.response.LoginResponse
import com.rangga.storyapp.data.retrofit.ApiRequest
import com.rangga.storyapp.helper.TokenDatastore
import com.rangga.storyapp.helper.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.await
import retrofit2.awaitResponse

class AddStoryViewModel(private val context: Context) : ViewModel() {
    val loading: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData()

    fun uploadStories(uri:Uri, desc: String) {
        loading.value = true
        val imageFile = Utils.uriToFile(uri, context)

        val requestBody = desc.toRequestBody("text/plain".toMediaType())
        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            requestImageFile
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiRequest.getApiService(context).postStories(multipartBody, requestBody).awaitResponse()
                if(response.isSuccessful){
                    val data = response.body()
                    isSuccess.postValue(true)
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
            }


    }}
}