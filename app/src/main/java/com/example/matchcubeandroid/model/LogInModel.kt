package com.example.matchcubeandroid.model

// JSON 데이터를 담을 클래스들을 생성

data class LogInModel(
        val statusCode: Int,
        val responseMessage: String,
        val data: LogInModelData
        //val data: List<MovieDetailResponse>
)

data class LogInModelData(
    var accountId: Int,
    var name: String,
    var nickName: String,
    var status: String
)
