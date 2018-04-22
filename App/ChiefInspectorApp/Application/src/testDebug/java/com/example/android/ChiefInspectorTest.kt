package com.example.android

import com.example.android.data.remote.ChiefInspectorApi
import com.example.android.factory.MultiPartFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi

import io.reactivex.observers.TestObserver
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.Retrofit

import java.io.File
import okhttp3.RequestBody
import okhttp3.MultipartBody
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.SECONDS




@RunWith(MockitoJUnitRunner::class)
class ChiefInspectorTest {
  internal lateinit var api: ChiefInspectorApi
  private var retrofit: Retrofit? = null
  private val okHttpClient: OkHttpClient? = null
  private var moshi: Moshi? = null
  lateinit var sessioniD: String


  private fun setupRetrofit() {
    val client = OkHttpClient.Builder()
      .connectTimeout(100, TimeUnit.SECONDS)
      .readTimeout(100, TimeUnit.SECONDS).build()

    retrofit = Retrofit.Builder().client(client)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(ScalarsConverterFactory.create())
      .baseUrl("http://pegeldaten.westeurope.cloudapp.azure.com:5000/")
      .build()
  }



  @Before
  @Throws(Exception::class)
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    setupRetrofit()

    api = retrofit!!.create(ChiefInspectorApi::class.java)

  }


  @Test
  fun upload() {
    val classLoader = javaClass.classLoader
    val file = File("/Users/jensklingenberg/Git/ChiefInspectorApp/Application/src/testDebug/java/com/example/android/Container.jpg")
    val body = MultiPartFactory.createImageMultipart(file)
    val name = RequestBody.create(MediaType.parse("text/plain"), "upload")
    val observer = api.uploadImage(body,name).test()
    val response = observer.assertNoErrors()

   Assert.assertNotEquals("",response)



  }

  @Test
  fun requestNew() {

    val observer = api.requestNew().test()
    val response = observer.assertNoErrors()


//

  }






}

