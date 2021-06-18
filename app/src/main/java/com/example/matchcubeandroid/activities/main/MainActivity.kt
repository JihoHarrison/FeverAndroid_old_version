package com.example.matchcubeandroid.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.matchcubeandroid.*
import com.example.matchcubeandroid.activities.login.LoginActivity
import com.example.matchcubeandroid.fragments.*
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

//1. (앱 - 카카오톡 서버)
//- 안드로이드 앱에서 "카카오톡 로그인" 버튼을 클릭하면, 카카오톡 서버와 인증을 수행
//- 카카오톡 서버와의 인증에 성공하면, 카카오톡으로부터 Access Token 을 획득
//2. (앱 - was 서버)
//- 획득된 Access Token을 장고 서버 인증 Endpoint(/accounts/rest-auth/kakao/)를 통해, JWT 토큰 획득
//- 획득한 JWT 토큰이 만료되기 전에, 갱신(refresh)
//- 획득한 JWT 토큰이 만료되었다면, Access Token을 서버로 전송하여 JWT 토큰 재획득

private const val TAG_MATCH_FRAGMENT = "match_fragment"
private const val TAG_MESSANGER_FRAGMENT = "messanger_fragment"
private const val TAG_CUBE_FRAGMENT = "cube_fragment"
private const val TAG_COACH_FRAGMENT = "coach_fragment"
private const val TAG_MYPAGE_FRAGMENT = "mypage_fragment"

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation_view.setOnNavigationItemSelectedListener(this)

        val fragMath = MatchFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragMath).commit()
    }


    /**프래그먼트가 BackStack에 하나씩 쌓이고 stack 형식에 맞게 back버튼이 눌렸을 경우 하나씩 지워가는 형식**/
    /**popBackStack**/
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fm = supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()
        when(item.itemId){
            R.id.tabMatch -> {
                fm.popBackStack("match", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val fragMath = MatchFragment()
                transaction.replace(R.id.frame_layout, fragMath, "match")
                transaction.addToBackStack("match")
            }
            R.id.tabMessanger -> {
                fm.popBackStack("messanger", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val fragMessanger = MessengerFragment()
                transaction.replace(R.id.frame_layout, fragMessanger,"messanger")
                transaction.addToBackStack("messanger")
            }
            R.id.tabCubeGround -> {
                fm.popBackStack("cube", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val fragCubeGround = CubeGroundFragment()
                transaction.replace(R.id.frame_layout, fragCubeGround)
                transaction.addToBackStack("cube")
            }
            R.id.tabCoach -> {
                fm.popBackStack("coach", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val fragCoach = CoachFragment()
                transaction.replace(R.id.frame_layout, fragCoach)
                transaction.addToBackStack("coach")
            }
            R.id.tabMyPage -> {
                fm.popBackStack("myPage", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val fragMyPage = MyPageFragment()
                transaction.replace(R.id.frame_layout, fragMyPage)
                transaction.addToBackStack("myPage")
            }
        }

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
        transaction.isAddToBackStackAllowed
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
