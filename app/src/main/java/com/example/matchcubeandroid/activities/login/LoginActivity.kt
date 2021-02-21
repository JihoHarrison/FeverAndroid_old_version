package com.example.matchcubeandroid.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.activities.register.RegisterActivity
import com.example.matchcubeandroid.model.signin
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.retrofit.SharedPref
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    val PREFERENCE = "template.android.matchCube"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        SharedPref.openSharedPrep(this)
        btnLogin.setOnClickListener {
            Client.retrofitService.signIn(
                editId.text.toString(),
                editPw.text.toString())
                .enqueue(object : retrofit2.Callback<Void> {
                    override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
//                        response?.body()?.let {
//                            val statusCode = it.statusCode
//                        }
                        when (response!!.code()) {
                         200 -> {
                             val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                             val editor = pref.edit()
                             editor.putString("username", editId.text.toString())
                             editor.commit()
                             finish()
                             startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                         }
                         405 -> Toast.makeText(this@LoginActivity, "로그인 실패 : 아이디나 비번이 올바르지 않습니다", Toast.LENGTH_LONG).show()
                         500 -> Toast.makeText(this@LoginActivity, "로그인 실패 : 서버 오류", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    }
                })
        }

        btnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}