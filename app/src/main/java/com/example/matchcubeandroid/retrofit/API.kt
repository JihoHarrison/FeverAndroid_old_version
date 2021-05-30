package com.example.matchcubeandroid.retrofit

import com.example.matchcubeandroid.model.*
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
    @GET("city")
    fun locate(): Call<LocateModel>

    // 시*도 별로 가져오는 거임 (시-군-구)
    @Headers("accept: application/json",
            "content-type: application/json")
    @GET("city/{cityCode}/si-gun-gu")
    fun locateDetail(@Path("cityCode") cityCode: Int): Call<LocateModel>

    // 개인정보 가져오는 api
    @Headers("accept: application/json",
            "content-type: application/json")
    @GET("myPage/{accountId}")
    fun accountId(@Path("accountId") accountId: Int): Call<AccountIdModel>

    // 팀 정보 가져오는 api
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("myPage/{accountId}/teams")
    fun myTeams(@Path("accountId") teamId: Long): Call<MyTeamsModel>

    // 팀의 세부 정보 가져오는 api
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("team")
    fun myTeamsDetail(
        @Query("categoryId") categoryId: Int,
        @Query("teamName") teamName: String,
        @Query("teamArea") teamArea: String,
        @Query("order") order: String,
        @Query("wantPlayer") wantPlayer: String,
        @Query("wantMercenary") wantMercenary: String,
        @Query("wantMatch") wantMatch: String): Call<MatchTeamsDetailModel>

    // 선수 세부정보 불러오는 api
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("players/{userId}/detail")
    fun playersDetail(@Path("userId") userId: Long): Call<PlayerDetailModel>
    // ID, PW 찾기

    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @PATCH("update") // 로그인
    fun update(
        @Body params: HashMap<String, String>
    ): Call<AccountIdModel>

    // 회원가입 여부 조회 API
    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("user")
    fun isExstUser(
        @Query("emailId") emailId: String,
        @Query("socialType") socialType: String
    ): Call<DefaultResponseModel>

    // 회원가입 API
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("user")
    fun register(
        @Body params: HashMap<String, Any>
    ): Call<DefaultResponseModel>


}