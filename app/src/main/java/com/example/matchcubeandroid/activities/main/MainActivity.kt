package com.example.matchcubeandroid.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.matchcubeandroid.*
import com.example.matchcubeandroid.activities.login.LoginActivity
import com.example.matchcubeandroid.fragments.LiveFragment
import com.example.matchcubeandroid.fragments.MatchFragment
import com.example.matchcubeandroid.fragments.MessengerFragment
import com.example.matchcubeandroid.fragments.MyPageFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    // Godd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            // 로그인 상태인지 체크후 아니면 로그인 액티비티로 전환
            if ( 1 == 0 ){
            }
            else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
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
            // 로그인 상태인지 체크후 아니면 로그인 액티비티로 전환
            if ( 1 == 0 ){
            }
            else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnetview, MyPageFragment())
                    .commit()
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
}