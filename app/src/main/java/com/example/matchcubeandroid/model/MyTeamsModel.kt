package com.example.matchcubeandroid.model

import retrofit2.http.Url

data class MyTeamsModel (
    val statusCode: Int,
    val responseMessage: String,
    val data: List<MyTeamsInfoModel>)

data class MyTeamsInfoModel(

    var teamId: Long,
    var teamName: String,
    var teamImageUrl: Url,
    var isTeamLeader: String,
    var memberCount: Int,
    var averageAge: Int,
    var athleteCount: Int
)