package com.example.matchcubeandroid.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matchcubeandroid.*
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
                    .replace(R.id.fragmnetview, MyPageFragment())
                    .commit()
        }
    }


}