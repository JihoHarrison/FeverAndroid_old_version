package com.example.matchcubeandroid.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.matchcubeandroid.*
import com.example.matchcubeandroid.activities.login.LoginActivity
import com.example.matchcubeandroid.fragments.*
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_main.*

//1. (앱 - 카카오톡 서버)
//- 안드로이드 앱에서 "카카오톡 로그인" 버튼을 클릭하면, 카카오톡 서버와 인증을 수행
//- 카카오톡 서버와의 인증에 성공하면, 카카오톡으로부터 Access Token 을 획득
//2. (앱 - was 서버)
//- 획득된 Access Token을 장고 서버 인증 Endpoint(/accounts/rest-auth/kakao/)를 통해, JWT 토큰 획득
//- 획득한 JWT 토큰이 만료되기 전에, 갱신(refresh)
//- 획득한 JWT 토큰이 만료되었다면, Access Token을 서버로 전송하여 JWT 토큰 재획득

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                //Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            else if (tokenInfo != null) {
                //Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
            }
        }
        bottom_navigation_view.setOnNavigationItemSelectedListener(this)

        val fragMath = MatchFragment()
        supportFragmentManager.beginTransaction().
                replace(R.id.frame_layout, fragMath).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.tabMatch -> {
                val fragMath = MatchFragment()
                supportFragmentManager.beginTransaction().
                        replace(R.id.frame_layout, fragMath).commit()
            }
            R.id.tabMessanger -> {
                val fragMessanger = MessengerFragment()
                supportFragmentManager.beginTransaction().
                replace(R.id.frame_layout, fragMessanger).commit()
            }
            R.id.tabCubeGround -> {
                val fragCubeGround = CubeGroundFragment()
                supportFragmentManager.beginTransaction().
                replace(R.id.frame_layout, fragCubeGround).commit()
            }
            R.id.tabCoach -> {
                val fragCoach = CoachFragment()
                supportFragmentManager.beginTransaction().
                replace(R.id.frame_layout, fragCoach).commit()
            }
            R.id.tabMyPage -> {
                val fragMyPage = MyPageFragment()
                supportFragmentManager.beginTransaction().
                replace(R.id.frame_layout, fragMyPage).commit()
            }
        }
        return true
    }

    override fun onDestroy() {
        if(MySharedPreferences.getAutoChecked(this).equals("N")){
            MySharedPreferences.clearUser(this)
        }
        super.onDestroy()
    }

    override fun onBackPressed() {

        super.onBackPressed()
    }




}
