package com.example.matchcubeandroid.model

/** 팀 세부정보 데이터 클래스  **/
data class TeamsDetailModel(

    val statusCode: Int,
    val responseMessage: String,
    val data: List<TeamDetail>
)
data class TeamDetail(

    var teamImageURL: String,
    var teamName: String,
    var wantMercenary: String,
    var wantPlayer: String,
    var wantMatch: String,
    var activeLog: String,
    var location: String,
    var member: String,
    var membershipFee: String,
    var memberAge: String,
    var uniform: String,
    var wasAthlete: String,
    var vehicle: String,
    var teamIntro: String
)