package com.example.matchcubeandroid.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Retrofit의 빌더생성
// Retrofit의 Builder를 통해 URL을 초기화하고 JSON을 위한 컨버터를 Gson으로 지정
// retrofit.create()의 API::class.java와 같은 형식은 자바 클래스의 인스턴스를 코틀린의 Class 인스턴스로 사용할 수 있게 변환.

object Client {

    // private const val BASE_URL_NAVER_API = "https://openapi.naver.com/"
    // private const val CLIENT_ID =
    // private const val CLIENT_SECRET =
    private const val BASE_URL = "http://10.0.2.2:8080/"


    val retrofitService: API

    init{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val logger = OkHttpClient.Builder().addInterceptor(interceptor).readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).build()
//10.0.2.2
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(logger)
                .build()

        retrofitService = retrofit.create(API::class.java)
    }
}