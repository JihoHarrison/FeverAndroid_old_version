package com.example.matchcubeandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences

class
MessengerFragment : Fragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messenger, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}