package com.example.matchcubeandroid.retrofit

import com.example.matchcubeandroid.model.*
import retrofit2.Call
import retrofit2.http.*
import kotlin.collections.HashMap

// REST API 요청을 위한 인터페이스

interface API {
    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @POST("login") // 로그인
//        @Field("emailId") emailId: String,
//        @Field("password") pwd: String
    fun logIn(
        @Body params: HashMap<String, String>
    ): Call<LogInModel>

    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @GET("city")
    fun locate(): Call<LocateModel>

    // 시*도 별로 가져오는 거임
    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @GET("city/{cityCode}/si-gun-gu")
    fun locateDetail(@Path("cityCode") cityCode: Int): Call<LocateModel>

    // 개인정보 가져오는 api
    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @GET("myPage/{accountId}")
    fun accountId(@Path("accountId") accountId: Int): Call<AccountIdModel>

    // 팀 정보 가져오는 api
    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @GET("myPage/{accountId}/teams")
    fun myTeams(@Path("accountId") teamId: Long): Call<MyTeamsModel>

    // 팀의 세부 정보 가져오는 api
    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @GET("team/{teamId}")
    fun myTeamsDetail(@Path("teamId") teamId: Int): Call<MatchtabTeamsModel>

    // 선수 세부정보 불러오는 api
    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @GET("players/{userId}/detail")
    fun playersDetail(@Path("userId") userId: Long): Call<PlayerDetailModel>
    // ID, PW 찾기

    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @PATCH("update") // 로그인
//        @Field("emailId") emailId: String,
//        @Field("password") pwd: String
    fun update(
        @Body params: HashMap<String, String>
    ): Call<AccountIdModel>
}

