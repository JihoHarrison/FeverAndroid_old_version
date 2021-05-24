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
import com.google.firebase.auth.FirebaseUser

class
MessengerFragment : Fragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_coach, container, false)
        var context: Context = view.context

        val account = GoogleSignIn.getLastSignedInAccount(context)
        Log.d("JT Test1", "account : " + account)
        if(account == null){
            startActivity(Intent(context, LoginActivity::class.java))
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}