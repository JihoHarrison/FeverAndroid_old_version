package com.example.matchcubeandroid.retrofit

import com.example.matchcubeandroid.model.LogInModel
import retrofit2.Call
import retrofit2.http.*

// REST API 요청을 위한 인터페이스

interface API {
    @POST("logIn") // 로그인
    @FormUrlEncoded
    fun logIn(
        @Field("emailId") emailId: String,
        @Field("password") pwd: String
    ): Call<LogInModel>

    @POST("signup") // 회원가입
    @FormUrlEncoded
    fun signUp(
        @Field("emailId") emailId: String,
        @Field("pwd") pwd: String,
        @Field("name") name: String,
        @Field("id") id: String
    ): Call<Void>


}