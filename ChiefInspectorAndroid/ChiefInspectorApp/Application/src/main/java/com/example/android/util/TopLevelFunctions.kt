package com.example.android.util

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun runInBackground(function: () -> Unit) {
  Observable.fromCallable {
    function()

    true
  }
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe { result ->
      //Use result for something
    }
}