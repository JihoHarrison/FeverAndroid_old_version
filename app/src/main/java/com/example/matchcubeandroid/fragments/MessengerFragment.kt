package com.example.matchcubeandroid.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.login.LoginActivity
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class
MessengerFragment : Fragment()  {

    //firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_coach, container, false)
        var context: Context = view.context

        //firebase auth 객체
        firebaseAuth = FirebaseAuth.getInstance()
        toMainActivity(firebaseAuth?.currentUser)
        return view
    }

    fun toMainActivity(user: FirebaseUser?) {
        if(user ==null) { // MainActivity 로 이동
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}