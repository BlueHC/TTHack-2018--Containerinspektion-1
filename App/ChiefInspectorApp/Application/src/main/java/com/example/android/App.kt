package com.example.android

import android.app.Application
import com.example.android.data.remote.ChiefInspectorApi
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class App : Application() {
  lateinit var retrofit: ChiefInspectorApi

  companion object {
    lateinit var instance: App

  }

  override fun onCreate() {
    super.onCreate()
    instance = this
    setupRetrofit()

  }

  private fun setupRetrofit() {

    retrofit = Retrofit.Builder()
      .baseUrl(ChiefInspectorApi.BASE_URL)
      .addConverterFactory(ScalarsConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build().create(ChiefInspectorApi::class.java)
  }

}