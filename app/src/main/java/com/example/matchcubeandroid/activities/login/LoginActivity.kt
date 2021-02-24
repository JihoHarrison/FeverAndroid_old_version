package com.example.matchcubeandroid.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.activities.register.RegisterActivity
import com.example.matchcubeandroid.model.LogInModel
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.retrofit.SharedPref
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response
import kotlin.Result.Companion.success

class LoginActivity : AppCompatActivity() {

    val PREFERENCE = "template.android.matchCube"
    private val TAG = "juntae"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        SharedPref.openSharedPrep(this)
        btnLogin.setOnClickListener {
            // 서비스 호출부
            Client.retrofitService.logIn(
                editId.text.toString(),
                editPw.text.toString())
                .enqueue(object : retrofit2.Callback<LogInModel> {
                    override fun onResponse(call: Call<LogInModel>, response: Response<LogInModel>) {

                        if (response.body()?.statusCode == 200){ // 200 : successful
                            val data = response.body()?.data
                            data?.let { success(data) }
                            val pref = getSharedPreferences(PREFERENCE, MODE_PRIVATE)
                            val editor = pref.edit()
                            // 자동 로그인 처리 ( 로그아웃 버튼 필요 )
                            if ( checkAutoLogin.isChecked()) {
                                response.body()?.data?.accountId?.let { it1 -> editor.putInt("accountId", it1.toInt()) }
                                editor.putString("name", response.body()?.data?.name)
                                editor.putString("nickName", response.body()?.data?.nickName)
                            }
                            editor.commit()
                            finish()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                        } else{
                            Toast.makeText(this@LoginActivity, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "${response.body()?.toString()}")
                        }
                    }

                    override fun onFailure(call: Call<LogInModel>?, t: Throwable?) {
                        t?.message?.let {
                            Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }
        btnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}