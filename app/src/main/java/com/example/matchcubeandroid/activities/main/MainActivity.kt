package com.example.matchcubeandroid.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matchcubeandroid.*
import com.example.matchcubeandroid.activities.login.LoginActivity
import com.example.matchcubeandroid.fragments.LiveFragment
import com.example.matchcubeandroid.fragments.MatchFragment
import com.example.matchcubeandroid.fragments.MessengerFragment
import com.example.matchcubeandroid.fragments.MyPageFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // Godd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


}