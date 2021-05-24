package com.example.matchcubeandroid.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.login.LoginActivity
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences

class CoachFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_coach, container, false)

        return view
    }

    override fun onDestroy() {
//        if(MySharedPreferences.getAutoChecked(requireContext()).equals("N")){
//            MySharedPreferences.clearUser(requireContext())
//        }
        super.onDestroy()
    }

}