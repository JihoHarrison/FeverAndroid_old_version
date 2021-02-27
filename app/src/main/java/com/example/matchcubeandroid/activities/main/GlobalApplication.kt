package com.example.matchcubeandroid.activities.main

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val appInfo: ApplicationInfo = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
        val aBundle: Bundle = appInfo.metaData
        val aValue: String? = aBundle.getString("aKey");

        if (aValue != null) {
            KakaoSdk.init(this, aValue)
        }
    }
}