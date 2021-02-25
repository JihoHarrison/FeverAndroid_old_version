package com.example.matchcubeandroid.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.login.LoginActivity
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import kotlinx.android.synthetic.main.fragment_my_page.*

class MyPageFragment : Fragment(), View.OnClickListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_my_page, container, false)
        val btn: Button = view.findViewById(R.id.logoutbt)
        btn.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.logoutbt -> {
                MySharedPreferences.clearUser(requireContext())
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }
    }

    override fun onDestroy() {
        if(MySharedPreferences.getAutoChecked(requireContext()).equals("N")){
            MySharedPreferences.clearUser(requireContext())
        }
        super.onDestroy()
    }
}