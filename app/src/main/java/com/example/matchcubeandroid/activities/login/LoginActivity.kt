package com.example.matchcubeandroid.activities.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

//    private var btnLogin: Button? = null
    //private var btnRegister: Button? = null
    private var editId: EditText? = null
    private var editPw: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin?.setOnClickListener {
            Toast.makeText(applicationContext, "Login 구현 해야함!", Toast.LENGTH_SHORT).show()
        }

        btnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }
}