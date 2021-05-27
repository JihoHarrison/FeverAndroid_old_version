package com.example.matchcubeandroid.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.model.DefaultResponseModel
import com.example.matchcubeandroid.retrofit.Client
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.SharedPreferences
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        lateinit var gender: String
        var termsOfService: Int = 0
        var privacyPolicy: Int = 0
        var emailId = intent.getStringExtra("emailId")
        var socialType = intent.getStringExtra("socialType")
        txtEmail.setText(emailId.toString())

        radioGroupGender.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioMale -> gender = "M"
                R.id.radioFemale -> gender = "F"
            }
        }

        termsOfServiceAgree.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.useAgreeRadio -> termsOfService = 1
            }
        }

        privacyPolicyAgree.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.infoAgreeRadio -> privacyPolicy = 1
            }
        }

        registerBtn.setOnClickListener {register(
            emailId.toString(),
            txtName.text.toString(),
            txtNickname.text.toString(),
            gender,
            txtPhoneNum.text.toString(),
            txtBirth.text.toString(),
            termsOfService,
            privacyPolicy,
            socialType.toString()
        )}
    }

    // 회원 등록
    private fun register(emailId: String,
                         name: String,
                         nickName: String,
                         sex: String,
                         phoneNumber: String,
                         birthday: String,
                         termsOfService: Int,
                         privacyPolicy: Int,
                         socialType: String){
        var body = HashMap<String, Any>()
        body.put("emailId", emailId)
        body.put("name", name)
        body.put("nickName", nickName)
        body.put("sex", sex)
        body.put("phoneNumber", phoneNumber)
        body.put("birthday", birthday)
        body.put("termsOfService", termsOfService)
        body.put("privacyPolicy", privacyPolicy)
        body.put("socialType", socialType)

        Client.retrofitService.register(body).enqueue(object : Callback<DefaultResponseModel> {
            override fun onResponse(call: Call<DefaultResponseModel>, response: Response<DefaultResponseModel>) {
                if(response.body()!!.statusCode == 100){
                    Toast.makeText(this@RegisterActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()

                    MySharedPreferences.setUserId(this@RegisterActivity,response.body()!!.accountId )
                    MySharedPreferences.setSocialType(this@RegisterActivity, socialType)

                    Log.d("account", "account  : " + MySharedPreferences.getUserId(this@RegisterActivity) )

                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@RegisterActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()

                }
            }
            override fun onFailure(call: Call<DefaultResponseModel>, t: Throwable) {
                t?.message?.let {
                    Toast.makeText(this@RegisterActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
