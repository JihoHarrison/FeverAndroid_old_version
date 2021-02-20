package com.example.matchcubeandroid.activities.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.retrofit.Client
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener{ // 회원 가입 버튼 클릭 이벤트 리스너
            Client.retrofitService.signUp(
                editId.text.toString(),
                editPw.text.toString(),
                editEmail.text.toString(),
                editName.text.toString())
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                        when (response!!.code()) {
                            200 -> {
                             Toast.makeText(this@RegisterActivity, "회원가입 성공", Toast.LENGTH_LONG).show()
                             finish ()
                         }
                          405 -> Toast.makeText(this@RegisterActivity, "회원가입 실패 : 아이디나 비번이 올바르지 않습니다", Toast.LENGTH_LONG).show()
                         500 -> Toast.makeText(this@RegisterActivity, "회원가입 실패 : 서버 오류", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    }
                })
        }
    }
}