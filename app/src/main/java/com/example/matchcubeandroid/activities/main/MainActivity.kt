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
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.main_toolbar.*

class MainActivity : AppCompatActivity(),  NavigationView.OnNavigationItemSelectedListener{
    // Godd
    override fun onBackPressed() {
        if(main_drawer_layout.isDrawerOpen(GravityCompat.START)){
            main_drawer_layout.closeDrawers()
            // 테스트를 위해 뒤로가기 버튼시 Toast 메시지
            Toast.makeText(this,"back btn clicked",Toast.LENGTH_SHORT).show()
        } else{
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.account-> Toast.makeText(this,"account clicked",Toast.LENGTH_SHORT).show()
            R.id.item2-> Toast.makeText(this,"item2 clicked",Toast.LENGTH_SHORT).show()
            R.id.item3-> Toast.makeText(this,"item3 clicked",Toast.LENGTH_SHORT).show()
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setSupportActionBar(main_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_android_drawer) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                activity_main.openDrawer(GravityCompat.START)
            }
        }
        return true
    }


}