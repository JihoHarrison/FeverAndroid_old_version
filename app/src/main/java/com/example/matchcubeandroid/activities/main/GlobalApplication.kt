package com.example.matchcubeandroid.activities.main

import android.app.Application
import android.util.Log
import com.example.matchcubeandroid.R
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    private val TAG = "retrofit"

    override fun onCreate() {
        super.onCreate()

        val aValue: String = resources.getString(R.string.kakao_app_key)
        //Log.d(TAG, "meta : " + "${aValue}")

        KakaoSdk.init(this, aValue)
    }
}