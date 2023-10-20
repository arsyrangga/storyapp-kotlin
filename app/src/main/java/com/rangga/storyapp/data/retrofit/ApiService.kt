package com.rangga.storyapp.data.retrofit


import com.rangga.storyapp.data.parcel.LoginParcel
import com.rangga.storyapp.data.parcel.RegistParcel
import com.rangga.storyapp.data.response.AddStoryResponse
import com.rangga.storyapp.data.response.DetailStoryResponse
import com.rangga.storyapp.data.response.FileUploadResponse
import com.rangga.storyapp.data.response.ListStoryResponse
import com.rangga.storyapp.data.response.LoginResponse
import com.rangga.storyapp.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("register")
    fun register(@Body post: RegistParcel): Call<RegisterResponse>

    @POST("login")
    fun login(@Body post: LoginParcel): Call<LoginResponse>

    @GET("stories")
    fun getStories(): Call<ListStoryResponse>

    @GET("stories/{id}")
    fun getDetailStories(@Path("id") id: String?): Call<DetailStoryResponse>


    @Multipart
    @POST("stories")
    fun postStories(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<FileUploadResponse>
}