package com.example.matchcubeandroid.activities.main

import com.kakao.sdk.common.model.AuthErrorCause.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.matchcubeandroid.*
import com.example.matchcubeandroid.activities.login.LoginActivity
import com.example.matchcubeandroid.fragments.LiveFragment
import com.example.matchcubeandroid.fragments.MatchFragment
import com.example.matchcubeandroid.fragments.MessengerFragment
import com.example.matchcubeandroid.fragments.MyPageFragment
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // SharedPreferences 안에 값이 저장되어 있지 않을 때 -> Login
        if(MySharedPreferences.getUserId(this).isNullOrBlank() || MySharedPreferences.getUserPass(this).isNullOrBlank()) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        else if (MySharedPreferences.getAutoChecked(this).equals("Y")){ // SharedPreferences 안에 값이 저장되어 있을 때 -> MainActivity로 이동
            Toast.makeText(this, "${MySharedPreferences.getUserId(this)}님 자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "${MySharedPreferences.getUserId(this)}님 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
        }

        btnNavi.setOnClickListener(){
            layout_drawer.openDrawer(GravityCompat.START) // START : LEFT, END : RIGHT
        }

        naviView.setNavigationItemSelectedListener(this) // 네비게이션 메뉴 item에 클릭 속성을 부여. (onClickListener 역할)

        match.setOnClickListener{
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnetview, MatchFragment())
                    .commit()
        }

        messenger.setOnClickListener{
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnetview, MessengerFragment())
                    .commit()
        }

        live.setOnClickListener{
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnetview, LiveFragment())
                    .commit()
        }

        mypage.setOnClickListener{
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentlinearLayout, MyPageFragment())
                    .commit()
            btnNavi.visibility = View.GONE // 마이페이지 진입 시 네비게이션 메뉴 숨김 -> 뒤로가기 버튼 추가 할 예정 or 로그아웃 버튼으로 수행

        }




    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean { // 네비게이션 메뉴 아이템 클릭 시 실행되는 메서드
        when(item.itemId){
            R.id.item1 -> Toast.makeText(applicationContext, "1번", Toast.LENGTH_SHORT).show()
            R.id.item2 -> Toast.makeText(applicationContext, "2번", Toast.LENGTH_SHORT).show()
            R.id.item3 -> Toast.makeText(applicationContext, "3번", Toast.LENGTH_SHORT).show()
        }
        layout_drawer.closeDrawers()
        return false
    }

    override fun onBackPressed() {
        if(layout_drawer.isDrawerOpen(GravityCompat.START)){ // DrawerView가 열려있을 경우
            layout_drawer.closeDrawers() // 백 버튼 누를 시 드로어 닫힘
        }
        else{
            super.onBackPressed() // 열려있지 않을 경우 일반 finish 메서드 실행
        }
    }

    override fun onDestroy() {
        if(MySharedPreferences.getAutoChecked(this).equals("N")){
            MySharedPreferences.clearUser(this)
        }
        super.onDestroy()
    }


}
