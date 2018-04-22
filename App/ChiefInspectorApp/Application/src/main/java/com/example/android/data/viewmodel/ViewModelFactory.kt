package com.example.android.data.viewmodel

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.annotation.VisibleForTesting
import android.support.v4.app.FragmentActivity
import com.example.android.App
import com.example.android.data.remote.ChiefInspectorApi


class ViewModelFactory private constructor(val chiefInspectorApi: ChiefInspectorApi) :
  ViewModelProvider.NewInstanceFactory() {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(InspectorViewModel::class.java)) {
      return InspectorViewModel(chiefInspectorApi) as T
    }

    throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
  }

  companion object {

    @SuppressLint("StaticFieldLeak")
    @Volatile
    private var INSTANCE: ViewModelFactory? = null


    fun getInstance(): ViewModelFactory? {

      if (INSTANCE == null) {
        val chiefInspectorApi = App.instance.retrofit
        synchronized(ViewModelFactory::class.java) {
          INSTANCE = ViewModelFactory(chiefInspectorApi)
        }
      }


      return INSTANCE
    }

    @JvmStatic
    fun obtainInspectorViewModel(activity: FragmentActivity): InspectorViewModel {
      // Use a Factory to inject dependencies into the ViewModel
      val factory = getInstance()
      return ViewModelProviders.of(activity, factory!!).get(InspectorViewModel::class.java)
    }


  }


}
