package com.example.matchcubeandroid.retrofit

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object SharedPref {
    val LOGIN_SESSION = "login.session"

    private var sharedPref: SharedPreferences? = null

    fun openSharedPrep(context: Context){
        this.sharedPref = context.getSharedPreferences(LOGIN_SESSION, Context.MODE_PRIVATE)
    }

    fun writeLoginSession(data: String){
        if(this.sharedPref == null){
            Log.e("DSMAD", "Plz start opensharedPrep() !")
        } else{
            sharedPref?.edit()?.putString("session", data)?.apply()
        }
    }

    fun readLoginSession(): String?{
        return if(this.sharedPref == null){
            Log.e("DSMAD", "Plz start openSharedPrep() !")
            null
        } else sharedPref?.getString("session", null)
    }

}