package com.example.matchcubeandroid.model


data class AccountIdModel(
        val statusCode: Int,
        val responseMessage: String,
        val data: AccountIdModelData
)

data class AccountIdModelData(
        var accountId: Int,
        var profileImage: String,
        var nickName: String,
        var name: String,
        var emailId: String
)