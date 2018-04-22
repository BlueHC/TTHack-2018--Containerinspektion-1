package com.example.android.factory

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part
import okhttp3.RequestBody
import java.io.File

class MultiPartFactory{

  companion object {
    fun createImageMultipart( file: File): Part {
      val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
      return MultipartBody.Part.createFormData("file", file.name, reqFile)
    }
  }



}