package com.example.matchcubeandroid.fragments

import android.content.Context
import android.os.Bundle
import android.service.autofill.FieldClassification
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.adapter.MatchTabTeamPlrAdapter
import com.example.matchcubeandroid.model.LocateModel
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import kotlinx.android.synthetic.main.fragment_match.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchFragment : Fragment()  {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)


        val tabAdapter = MatchTabTeamPlrAdapter(fragmentManager)





        matchTabLayout.setupWithViewPager(matchViewPager)

        var context: Context = view.context
        var cityCode: Int = 11 // 서울 cityCode

        














        // api interface : city -> 시 * 도만 출력
        Client.retrofitService.locate().enqueue(object : Callback<LocateModel> {
            override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                if (response.body()?.statusCode == 100) { // 200 : successful
                    val data = response.body()?.data
                    data?.let { Result.success(data) }
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                    Log.d("locateTag", "${response.body()?.toString()}")
                } else {
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LocateModel>, t: Throwable) {
                t?.message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }
        })

        // PATH variable -> {cityCode}
        // api interface : city/{cityCode}/si-gun-gu -> 시*도 별로 구*군 출력
        Client.retrofitService.locateDetail(cityCode).enqueue(object : Callback<LocateModel> {
            override fun onResponse(call: Call<LocateModel>, response: Response<LocateModel>) {
                if (response.body()?.statusCode == 100) { // 200 : successful
                    val data = response.body()?.data
                    data?.let { Result.success(data) }
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                    Log.d("locateDetail", "${response.body()?.toString()}")

                } else {
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
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