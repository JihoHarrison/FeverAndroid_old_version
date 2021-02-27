package com.example.matchcubeandroid.activities.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.model.SignUpModel
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.btnRegister
import kotlinx.android.synthetic.main.activity_register.editPw

class RegisterActivity : AppCompatActivity() {

    private val TAG = "retrofit"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister.setOnClickListener{ // 회원 가입 버튼 클릭 이벤트 리스너
            val body = HashMap<String, String>()
            body.put("emailId", editEmail.text.toString() )
            body.put("password", editPw.text.toString() )
            body.put("name", editName.text.toString() )

            Client.retrofitService.signUp(body).enqueue(object : Callback<SignUpModel> {
                    override fun onResponse(call: Call<SignUpModel>?, response: Response<SignUpModel>?) {
                        if (response?.body()?.statusCode == 200){ // 200 : successful

                            MySharedPreferences.setAutoChecked(this@RegisterActivity, "N")
                            MySharedPreferences.setUserId(this@RegisterActivity, editEmail.text.toString())
                            MySharedPreferences.setUserPass(this@RegisterActivity, editPw.text.toString())

                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else{
                            Toast.makeText(this@RegisterActivity, response?.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "${response?.body()?.toString()}")
                        }
                    }

                    override fun onFailure(call: Call<SignUpModel>?, t: Throwable?) {
                        t?.message?.let {
                            Toast.makeText(this@RegisterActivity, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }
    }
}