package com.example.matchcubeandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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