package com.example.matchcubeandroid.activity.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.matchcubeandroid.R

class LoginActivity : AppCompatActivity() {

    private var btnLogin: Button? = null
    private var btnRegister: Button? = null
    private var editId: EditText? = null
    private var editPw: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById<Button>(R.id.btnLogin)
        btnRegister = findViewById<Button>(R.id.btnRegister)
        editId = findViewById<EditText>(R.id.editId)
        editPw = findViewById<EditText>(R.id.editPw)

        btnLogin?.setOnClickListener (object : View.OnClickListener{
            override fun onClick(v: View?) {
                // TODO
            }
        })

        btnRegister?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                // TODO
            }
        })
    }
}