package com.example.matchcubeandroid.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.EditMyProfileActivity
import com.example.matchcubeandroid.adapter.MatchtabTeamsAdapter
import com.example.matchcubeandroid.adapter.OptionAdapter
import com.example.matchcubeandroid.adapter.ProfileAdapter
import com.example.matchcubeandroid.model.*
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_edit_my_profile.*
import kotlinx.android.synthetic.main.fragment_matchtabteam.*
import kotlinx.android.synthetic.main.fragment_my_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPageFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    // 프로필 정보 불러오기 (수정필요)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_my_page, container, false)
        var context: Context = view.context
        val name: TextView = view.findViewById(R.id.txtName)
        var layoutInflater: LayoutInflater
        var myTeamsLayoutContainer: ViewGroup = view.findViewById(R.id.MyPageTeams)
        var profileList = ArrayList<ProfileModel>()
        val btn: TextView = view.findViewById(R.id.btnEditMyProfile)
        val rvTeamProfImgs: RecyclerView = view.findViewById(R.id.rvTeamProfImgs)
        val opsList: ListView = view.findViewById(R.id.options)

        Log.d("minjun", "account : " + MySharedPreferences.getUserId(context))

        Client.retrofitService.accountId(MySharedPreferences.getUserId(context)).enqueue(object : Callback<AccountIdModel> {
            override fun onResponse(
                call: Call<AccountIdModel>,
                response: Response<AccountIdModel>
            ) {

                if (response.body()?.statusCode == 100) { // 100 : successful
                    val data = response.body()?.data
                    data?.let { Result.success(data) }
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT)
                        .show()
                    Log.d("Account", "${response.body()?.toString()}")
                    name.setText(data?.name)

                    Glide.with(context).load(data?.profileImage).into(imgProfile)
                } else {
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT)
                        .show()
                    Log.d("d", "${response.body()?.toString()}")
                }
            }

            override fun onFailure(call: Call<AccountIdModel>, t: Throwable) {
                t?.message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }

        })

        Client.retrofitService.myTeams(1).enqueue(object : Callback<MyTeamsModel> {
            override fun onResponse(call: Call<MyTeamsModel>, response: Response<MyTeamsModel>) {


                if (response.body()?.statusCode == 204) { // 100 : successful
                    Toast.makeText(context, "팀 멤버 정보 조회 실패", Toast.LENGTH_SHORT).show()
                } else {
                    var dataSize: Int = response.body()!!.data?.size!!

                    if (dataSize == 0) { // 소속된 팀이 없을 경우
                        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                        layoutInflater.inflate(R.layout.default_my_teams_rc, myTeamsLayoutContainer, true)
                    } else if (dataSize != 0) {
                        for (i in 0 until dataSize) {
                            profileList.apply {
                                add(
                                    ProfileModel(
                                        response.body()!!.data?.get(i)?.teamImageUrl.toString(),
                                        response.body()!!.data?.get(i)?.teamName.toString()
                                    )
                                )
                            }
                            rvTeamProfImgs.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            rvTeamProfImgs.setHasFixedSize(true)
                            rvTeamProfImgs.adapter = ProfileAdapter(context, profileList)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MyTeamsModel>, t: Throwable) {
                t?.message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }

        })

        btn.setOnClickListener {
            startActivity(Intent(context, EditMyProfileActivity::class.java))
        }

        val optionList = arrayListOf<OptionModel>(
            //옵션 메뉴
            OptionModel("공지사항"),
            OptionModel("이용약관"),
            OptionModel("앱 설정"),
        )

        val Adapter = OptionAdapter(context, optionList)
        opsList.adapter = Adapter

        // 클릭이벤트 활성화 해야함(수정 필요)
        // 버튼 눌렀을때 해당 프레그먼트로 이동할수 있도록 변경해야함
        opsList.setOnItemClickListener { parent: AdapterView<*>, view: View, position: Int, id: Long ->
            if (position == 0) {
                Toast.makeText(context, "공지사항", Toast.LENGTH_SHORT).show()
            }
            if (position == 1) {
                Toast.makeText(context, "이용약관", Toast.LENGTH_SHORT).show()
            }
            if (position == 2) {
                Toast.makeText(context, "앱설정", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onDestroy() {
        if (MySharedPreferences.getAutoChecked(requireContext()).equals("N")) {
            MySharedPreferences.clearUser(requireContext())
        }
        super.onDestroy()
    }
}