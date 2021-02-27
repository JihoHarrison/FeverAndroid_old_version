package com.example.matchcubeandroid.retrofit

import com.example.matchcubeandroid.model.LogInModel
import com.example.matchcubeandroid.model.SignUpModel
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.HashMap

// REST API 요청을 위한 인터페이스

interface API {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("login") // 로그인
//        @Field("emailId") emailId: String,
//        @Field("password") pwd: String
    fun logIn(
        @Body params: HashMap<String, String>
    ): Call<LogInModel>

    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("signup") // 회원가입
    fun signUp(
        @Body params: HashMap<String, String>
//        @Field("emailId") emailId: String,
//        @Field("pwd") pwd: String,
//        @Field("name") name: String,
//        @Field("id") id: String
    ): Call<SignUpModel>


}