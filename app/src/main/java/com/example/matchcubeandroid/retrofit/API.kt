package com.example.matchcubeandroid.retrofit

import com.example.matchcubeandroid.model.signin
import retrofit2.Call
import retrofit2.http.*

interface API {
    @POST("api/signin") // 로그인
    @FormUrlEncoded
    fun signIn(
        @Field("emailId") emailId: String,
        @Field("pwd") pwd: String
    ): Call<Void>

    @POST("api/signup") // 회원가입
    @FormUrlEncoded
    fun signUp(
        @Field("emailId") emailId: String,
        @Field("pwd") pwd: String,
        @Field("name") name: String,
        @Field("id") id: String
    ): Call<Void>


}