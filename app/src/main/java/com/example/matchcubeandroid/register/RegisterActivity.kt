package com.example.matchcubeandroid.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.matchcubeandroid.R

class RegisterActivity : AppCompatActivity() {

    private var btnRegister: Button? = null // 회원가입 완료 버튼 선언
    private var editEmail: EditText? = null // 이메일 입력 EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister = findViewById<Button>(R.id.btnRegister) // 회원가입 버튼 클릭 이벤트 리스너 ID 찾아오기

        btnRegister?.setOnClickListener(object : View.OnClickListener{ // 회원 가입 버튼 클릭 이벤트 리스너
            override fun onClick(v: View?) {
                // TODO
            }
        })


    }
}