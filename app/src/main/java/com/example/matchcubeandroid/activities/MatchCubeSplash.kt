package com.example.matchcubeandroid.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.matchcubeandroid.activities.main.MainActivity

/* 스플래시 화면에서 메인 액티비티로 넘어가게 해 주는 코틀린 파일 */
class MatchCubeSplash : AppCompatActivity() {

    val SPLASH_VIEW_TIME : Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({ // delay를 위한 handler
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_VIEW_TIME)
    }
}