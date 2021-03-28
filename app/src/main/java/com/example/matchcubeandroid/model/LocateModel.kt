package com.example.matchcubeandroid.model

data class LocateModel(
    val statusCode: Int,
    val responseMessage: String,
    val data: List<LocateResponse>
)

data class LocateResponse(
    var code: Int,
    var name: String
)