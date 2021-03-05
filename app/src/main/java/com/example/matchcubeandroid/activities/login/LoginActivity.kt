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
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.*
import kotlin.Result.Companion.success
import com.kakao.sdk.common.model.AuthErrorCause.*

class LoginActivity : AppCompatActivity() {

    private val TAG = "retrofit"

   // kakao
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            when {
                error.toString() == AccessDenied.toString() -> {
                    Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == InvalidClient.toString() -> {
                    Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                }
                error.toString() == InvalidGrant.toString() -> {
                    Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                }
                error.toString() == InvalidRequest.toString() -> {
                    Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                }
                error.toString() == InvalidScope.toString() -> {
                    Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                }
                error.toString() == Misconfigured.toString() -> {
                    Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                }
                error.toString() == ServerError.toString() -> {
                    Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                }
                error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                    Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                }
                else -> { // Unknown
                    Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "${error.toString()}")
                }
            }
        }
        else if (token != null) {
            Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnKakaoLogin.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        btnLogin.setOnClickListener {
            val body = HashMap<String, String>()
            body.put("emailId", editId.text.toString() )
            body.put("password", editPw.text.toString() )
            // 서비스 호출부
            Client.retrofitService.logIn(body).enqueue(object : Callback<LogInModel> {
                    override fun onResponse(call: Call<LogInModel>, response: Response<LogInModel>) {

                        if (response.body()?.statusCode == 200){ // 200 : successful
                            val data = response.body()?.data
                            data?.let { success(data) }
                            // 자동 로그인 처리
                            if ( checkAutoLogin.isChecked()) {
                                MySharedPreferences.setAutoChecked(this@LoginActivity, "Y")
                            }
                            else{
                                MySharedPreferences.setAutoChecked(this@LoginActivity, "N")
                            }
                            // 데이터 저장
                            MySharedPreferences.setUserId(this@LoginActivity, editId.text.toString())
                            MySharedPreferences.setUserPass(this@LoginActivity, editPw.text.toString())

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

        btnKakaoLogin.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

    }
}