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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

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


    bottom_navigation_view.setOnNavigationItemSelectedListener(this)

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
