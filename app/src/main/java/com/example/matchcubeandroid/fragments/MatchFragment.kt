package com.example.matchcubeandroid.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.model.LocateModel
import com.example.matchcubeandroid.model.LogInModel
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)
        var context: Context = view.context
        // 서비스 호출부
        Client.retrofitService.locate().enqueue(object : Callback<LocateModel> {
            override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {

                if (response.body()?.statusCode == 200) { // 200 : successful
                    val data = response.body()?.data
                    data?.let { Result.success(data) }

                } else {
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                    Log.d("d", "${response.body()?.toString()}")
                }
            }

            override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                t?.message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        })



        return view
    }


    override fun onDestroy() {
        if(MySharedPreferences.getAutoChecked(requireContext()).equals("N")){
            MySharedPreferences.clearUser(requireContext())
        }
        super.onDestroy()
    }




}