package com.example.matchcubeandroid.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.adapter.MatchtabTeamsAdapter
import com.example.matchcubeandroid.image.URLtoBitmapTask
import com.example.matchcubeandroid.model.MatchtabTeamsModel
import com.example.matchcubeandroid.model.MyTeamsModel
import com.example.matchcubeandroid.retrofit.Client
import kotlinx.android.synthetic.main.fragment_matchtabteam.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.matchcubeandroid.model.TeamsDetailModel
import kotlinx.android.synthetic.main.match_team_list_item.*

class Matchtabteam : Fragment() {

    var myTeamName:ArrayList<String> = ArrayList()
    lateinit var matchtabTeamsAdapter: MatchtabTeamsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matchtabteam, container, false)

        var layoutInflater: LayoutInflater
        var myTeamsLayoutContainer: ViewGroup = view.findViewById(R.id.myTeamsLayout)
        var context: Context = view.context
        val accountId: Long = 1
        var myTeamId2: Int = 1
        val teamId: Long = 1
        var  i: Int = 0
        var teamsLists = ArrayList<MatchtabTeamsModel>()
        var teamsName = ArrayList<String>()
        var image_task: URLtoBitmapTask
        var bitmap: Bitmap

        Client.retrofitService.myTeamsDetail(1).enqueue(object: Callback<TeamsDetailModel>{
            override fun onResponse(call: Call<TeamsDetailModel>, response: Response<TeamsDetailModel>) {

                var dataSize: Int = response.body()!!.data?.size!!

                //var dataSize = 4;
                if(dataSize == 0){ // 소속된 팀이 없을 경우
                    //layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    //layoutInflater.inflate(R.layout.default_my_teams_rc, myTeamsLayoutContainer, true)

                } else if(dataSize != 0){   /** 소속팀이 있을 경우 **/
                    matchtabTeamsAdapter = MatchtabTeamsAdapter(context)

                    myTeamsRc.adapter = matchtabTeamsAdapter
                    teamsCompeteRc.adapter = matchtabTeamsAdapter
                    teamsToRegistRc.adapter = matchtabTeamsAdapter
                    teamsAsSoloRc.adapter = matchtabTeamsAdapter

                    for(i in i until dataSize) {
//                        image_task = URLtoBitmapTask().apply {
//                            url = URL(response.body()!!.data?.get(i).teamImageURL)
//                        }
//                        bitmap = image_task.execute().get()
//                        Glide.with(context).load(bitmap).into(imgTeams)



                        //Toast.makeText(context, response.body()!!.data?.get(i).teamName, Toast.LENGTH_SHORT).show()
                        teamsLists.apply {

                            /** 여기 희한하게 teamName이 이미 문자열인데도 toString 함수가 필요함. **/
                            /** 뭔가 add함수 안에 호출될때 다른 형식으로 변환되는걸 다시 문자열로 바꿔주는 형식인듯... **/
                            add(MatchtabTeamsModel(response.body()!!.data?.get(i).teamImageURL,
                                response.body()!!.data?.get(i).teamName,
                                response.body()!!.data?.get(i).teamIntro
                            ))
                            matchtabTeamsAdapter.teamsLists = teamsLists
                            matchtabTeamsAdapter.notifyDataSetChanged()

                        }
                    }

                }
//                myTeamsRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                teamsCompeteRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                teamsToRegistRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                teamsAsSoloRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//
//                myTeamsRc.adapter = MatchtabTeamsAdapter(teamsLists)
//                teamsToRegistRc.adapter = MatchtabTeamsAdapter(teamsLists)
//                teamsCompeteRc.adapter = MatchtabTeamsAdapter(teamsLists)
//                teamsAsSoloRc.adapter = MatchtabTeamsAdapter(teamsLists)

            }

            override fun onFailure(call: Call<TeamsDetailModel>, t: Throwable) {
                Toast.makeText(context, "실패예요", Toast.LENGTH_SHORT).show()
            }


        })

//            override fun onResponse(call: Call<TeamsDetailModel>, response: Response<TeamsDetailModel>) {
//                var dataSize: Int = response.body()!!.data?.size!!
//
//                if(dataSize == 0){ // 소속된 팀이 없을 경우
//                    layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//                    layoutInflater.inflate(R.layout.default_my_teams_rc, myTeamsLayoutContainer, true)
//
//                } else if(dataSize != 0){   /** 소속팀이 있을 경우 **/
//                        for(i in i until dataSize) {
//                            teamsLists.apply {
//                                /** 여기 희한하게 teamName이 이미 문자열인데도 toString 함수가 필요함. **/
//                                /** 뭔가 add함수 안에 호출될때 다른 형식으로 변환되는걸 다시 문자열로 바꿔주는 형식인듯... **/
//                                add(MatchtabTeamsModel(R.drawable.matchtab_teams_round,
//                                    response.body()!!.data?.get(i)?.teamName.toString(),
//                                    "안녕하세요"
//                                ))
//                            }
//                        }
//                    myTeamsRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                    teamsCompeteRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                    teamsToRegistRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                    teamsAsSoloRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//                    myTeamsRc.setHasFixedSize(true)
//                    teamsCompeteRc.setHasFixedSize(true)
//                    teamsToRegistRc.setHasFixedSize(true)
//                    teamsAsSoloRc.setHasFixedSize(true)
//                    myTeamsRc.adapter = MatchtabTeamsAdapter(teamsLists)
//                    teamsToRegistRc.adapter = MatchtabTeamsAdapter(teamsLists)
//                    teamsCompeteRc.adapter = MatchtabTeamsAdapter(teamsLists)
//                    teamsAsSoloRc.adapter = MatchtabTeamsAdapter(teamsLists)

            //                }
//            }
//            override fun onFailure(call: Call<MyTeamsModel>, t: Throwable) {
//                Toast.makeText(context, "내 팀 정보 조회 실패", Toast.LENGTH_SHORT).show()
//            }




        // floating action btn 클릭 시 내 팀의 목록 보여주는 클라이언트 객체
            Client.retrofitService.myTeams(teamId).enqueue(object: Callback<MyTeamsModel?>{
                // onResponse 부분에서 따로 성공했을 때의 상태코드 조건문 안만들어줘도 바로 불러와짐
                override fun onResponse(call: Call<MyTeamsModel?>, response: Response<MyTeamsModel?>) {
                    // 데이터 클래스 모델 만들때 URL타입 사용하면 오류납니다...;;
                    Toast.makeText(context, "호출에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    for(i in i until response.body()?.data?.size!!){
                        Log.d("TeamNum", response.body()!!.data?.size.toString())
                        myTeamName.add(response.body()!!.data!![i].teamName.toString())
                        Log.d("myTeamName",myTeamName[i])
                    }
                }
                override fun onFailure(call: Call<MyTeamsModel?>, t: Throwable) {
                    Toast.makeText(context, "호출에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            })



        return view
    }
}




