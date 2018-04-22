package com.example.android.util

import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

class RequestBodyExt{


  companion object {
    fun createImageBody(string: String,file: File): RequestBody {
return RequestBody.create(MediaType.parse("image/*"), file)
    }
  }



}