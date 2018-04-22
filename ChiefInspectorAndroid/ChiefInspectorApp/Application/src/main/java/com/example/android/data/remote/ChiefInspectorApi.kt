package com.example.android.data.remote


import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ChiefInspectorApi {

  @Multipart
 // @POST("image/add")
  @POST("pred/img")
  fun uploadImage(@Part  image: MultipartBody.Part,@Part("name")  name: RequestBody): Single<String>

 @GET("inspection/new")
 fun requestNew() :  Single<String>


  companion object {
    val BASE_URL = "https://chiefinspector.mybluemix.net/rest/main/"
  }


}