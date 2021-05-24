package com.example.matchcubeandroid.model

import java.math.BigInteger

data class MatchTeamsDetailModel (// 리사이클러뷰 하나 클릭했을 때 보여주는 팀 세부정보
    val statusCode: Int,
    val responseMessage: String,
    val data: ArrayList<TeamsInfoDetail>
)

data class TeamsInfoDetail(
    var teamImageUrl: String,
    var teamId: BigInteger,
    var teamName: String,
    var teamArea: String,
    var categoryId:	Int,
    var wantPlayer:	String,
    var wantMercenary: String,
    var wantMatch: String,
    var teamLeaderId: BigInteger,
    var activeLog: String,
    var famous:	Long,
    var teamIntro: String
)
