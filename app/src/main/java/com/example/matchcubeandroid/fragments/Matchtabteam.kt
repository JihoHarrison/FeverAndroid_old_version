package com.example.matchcubeandroid.fragments

import android.content.Context
import android.os.Bundle
import android.service.autofill.FieldClassification
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.model.LocateModel
import com.example.matchcubeandroid.model.MyTeamsModel
import com.example.matchcubeandroid.retrofit.Client
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_matchtabteam.*
import kotlinx.android.synthetic.main.fragment_matchtabteam.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Matchtabteam : Fragment() {

    val MyTeamName:ArrayList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matchtabteam, container, false)

        var context: Context = view.context
        val accountId: Long = 1;
        val teamId: Long = 1;

        view.floatingBtn.setOnClickListener{ it ->
            Client.retrofitService.myTeams(teamId).enqueue(object : Callback<MyTeamsModel>{
                override fun onResponse(call: Call<MyTeamsModel>, response: Response<MyTeamsModel>) {
                    if(response.body()?.statusCode == 100){
                        Log.d("TeamTeam", "hi")
                        val data = response.body()?.data
                        val bodyData = response.body()!!
                        print(bodyData.responseMessage)
//                        data?.let { Result.success(data) }
//                        var sizeArr = bodyData.data.size
//                        var i: Int = 0
//                        for(i in i..(sizeArr-1)){
//                            MyTeamName!![i] = bodyData.data[i].teamName
//                        }
                    } else if(response.body()?.statusCode == 204){
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show()

                    } else if(response.body()?.statusCode == 208){
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<MyTeamsModel>, t: Throwable) {
                    t?.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        return view
    }
}