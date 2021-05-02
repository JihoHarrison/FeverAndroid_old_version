package com.example.matchcubeandroid.model

data class TeamsModel(
        val statusCode: Int,
        val responseMessage: String,
        val data: List<TeamsModelData>
)

data class TeamsModelData(
        var teamId: Int,
        var teamName: String,
        var teamImageUrl: String,
        var memberCount: Int,
        var averageAge: Int,
        var athleteCount: Int
)