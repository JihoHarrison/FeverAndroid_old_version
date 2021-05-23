package com.example.matchcubeandroid.model

import android.telephony.PhoneNumberFormattingTextWatcher


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
        var emailId: String,
        var phoneNumber: String,
        var birthday: String,
        var sex: String

)