package com.example.matchcubeandroid.fragments

import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.service.autofill.FieldClassification
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.model.LocateModel
import com.example.matchcubeandroid.model.MyTeamsModel
import com.example.matchcubeandroid.model.TeamsModel
import com.example.matchcubeandroid.retrofit.Client
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.default_my_teams_rc.*
import kotlinx.android.synthetic.main.fragment_matchtabteam.*
import kotlinx.android.synthetic.main.fragment_matchtabteam.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.matchcubeandroid.fragments.MatchFragment


class Matchtabteam : Fragment() {

    var myTeamName:ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matchtabteam, container, false)
        var layoutInflater: LayoutInflater
        var myTeamsLayoutContainer: ViewGroup = view.findViewById(R.id.myTeamsLayout)
        var context: Context = view.context
        val accountId: Long = 1;
        val teamId: Long = 1;

        Client.retrofitService.myTeams(accountId).enqueue(object: Callback<MyTeamsModel> {
            override fun onResponse(call: Call<MyTeamsModel>, response: Response<MyTeamsModel>) {
                if(response.body()!!.data?.size != 0){ // 소속된 팀이 없을 경우
                    floatingBtn.hide()
                    layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    layoutInflater.inflate(R.layout.default_my_teams_rc, myTeamsLayoutContainer, true)
                                /** 소속팀이 없을 경우 **/
                    

                }
            }

            override fun onFailure(call: Call<MyTeamsModel>, t: Throwable) {
                Toast.makeText(context, "내 팀 정보 조회 실패", Toast.LENGTH_SHORT).show()
            }


        })

        val floatingBtn: FloatingActionButton = view.findViewById(R.id.floatingBtn)
        // floating action btn 클릭 시 내 팀의 목록 보여주는 클라이언트 객체
        floatingBtn.setOnClickListener{
            Client.retrofitService.myTeams(teamId).enqueue(object: Callback<MyTeamsModel?>{
                // onResponse 부분에서 따로 성공했을 때의 상태코드 조건문 안만들어줘도 바로 불러와짐
                override fun onResponse(call: Call<MyTeamsModel?>, response: Response<MyTeamsModel?>) {
                    var i:Int = 0
                    // 데이터 클래스 모델 만들때 URL타입 사용하면 오류납니다...;;
                    Toast.makeText(context, "호출에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    for(i in i..((response.body()?.data?.size!!) - 1)){
                        Log.d("TeamNum", response.body()!!.data?.size.toString())
                        myTeamName.add(response.body()!!.data!![i].teamName.toString())
                        Log.d("myTeamName",myTeamName[i])
                    }
                }

                override fun onFailure(call: Call<MyTeamsModel?>, t: Throwable) {
                    Toast.makeText(context, "호출에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }



        return view
    }
}

