package com.example.matchcubeandroid.model

data class AccountIdModel(
    val statusCode: Int,
    val responseMessage: String,
    val data: List<AccountIdModelData>
)

data class AccountIdModelData(
    var accountId: Int,
    var nickName: String,
    var name: String,
    var emailId: String,
    var teamId: Int,
    var teamIamgeUrl: String,
    var isTeamLeader: String
)