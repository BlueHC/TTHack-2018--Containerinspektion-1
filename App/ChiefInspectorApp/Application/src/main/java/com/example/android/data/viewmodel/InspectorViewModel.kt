package com.example.android.data.viewmodel


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.android.data.remote.ChiefInspectorApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class InspectorViewModel(val chiefInspectorApi: ChiefInspectorApi) : ViewModel() {

  private var sessionID = MutableLiveData<Int>()

   var sessionList= MutableLiveData<List<String>>()


  fun setSessionList() {

    sessionList.value=listOf("1","2","3","4","5","6","7","8","9","10")
  }

  fun getFilesReponseList(folderPath: String): List<File> {
    val directory = File(folderPath)
    return directory?.listFiles()?.toList() ?: emptyList()
  }

  fun uploadFile(string: String) {
    val file = File(string)

    val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
    val image = MultipartBody.Part.createFormData("file", file.name, reqFile)
    val name = RequestBody.create(MediaType.parse("text/plain"), "upload_test")


    chiefInspectorApi.uploadImage(image,name)
      .subscribeOn(Schedulers.newThread())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(object : DisposableSingleObserver<String>() {
        override fun onSuccess(xml: String) {
Log.d("this",xml)
        }

        override fun onError(e: Throwable) {
Log.d("this",e.message)
        }
      })

  }


}
