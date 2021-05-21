package com.example.matchcubeandroid.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.adapter.OptionAdapter
import com.example.matchcubeandroid.adapter.ProfileAdapter
import com.example.matchcubeandroid.image.URLtoBitmapTask
import com.example.matchcubeandroid.model.*
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL


class MyPageFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    // 프로필 정보 불러오기 (수정필요)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_my_page, container, false)
        var context: Context = view.context
        val name: TextView = view.findViewById(R.id.txtName)
        val profileimage: CircleImageView = view.findViewById(R.id.imgProfile)


        Client.retrofitService.accountId(1).enqueue(object : Callback<AccountIdModel> {
            override fun onResponse(call: Call<AccountIdModel>, response: Response<AccountIdModel>) {

                if (response.body()?.statusCode == 100) { // 100 : successful
                    val data = response.body()?.data
                    data?.let { Result.success(data) }
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                    Log.d("Account", "${response.body()?.toString()}")
                    name.setText(data?.name)

                    // 이미지 처리 객체
                    var image_task: URLtoBitmapTask = URLtoBitmapTask()
                    image_task = URLtoBitmapTask().apply {
                        url = URL(data?.profileImage)
                    }
                    var bitmap: Bitmap = image_task.execute().get()
                    profileimage.setImageBitmap(bitmap)
                }
                else {
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
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

                if (response.body()?.statusCode == 100) { // 100 : successful
                    val data = response.body()?.data
                    data?.let { Result.success(data) }
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                    Log.d("teamsId", "${response.body()?.toString()}")

                }
                else {
                    Toast.makeText(context, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                    Log.d("d", "${response.body()?.toString()}")
                }
            }

            override fun onFailure(call: Call<MyTeamsModel>, t: Throwable) {
                t?.message?.let {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            }

        })

        val btn: TextView = view.findViewById(R.id.btnEditMyProfile)
        val rvTeamProfImgs: RecyclerView = view.findViewById(R.id.rvTeamProfImgs)
        val opsList: ListView = view.findViewById(R.id.options)
        btn.setOnClickListener {


        }




        val profileList = arrayListOf( // DB에 저장된 값으로 수정 가능 할 것으로 보임!
            ProfileModel(R.drawable.ic_android_drawer, "토트넘")
        )

        rvTeamProfImgs.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvTeamProfImgs.setHasFixedSize(true)
        rvTeamProfImgs.adapter = ProfileAdapter(profileList)

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
        opsList.setOnItemClickListener { parent:AdapterView<*>, view:View, position:Int, id:Long ->
            if (position == 0) {
                Toast.makeText(context, "공지사항",Toast.LENGTH_SHORT).show()
            }
            if (position == 1) {
                Toast.makeText(context, "이용약관",Toast.LENGTH_SHORT).show()
            }
            if (position == 2) {
                Toast.makeText(context, "앱설정",Toast.LENGTH_SHORT).show()
            }
        }







// 클릭이벤트 활성화 해야함(수정 필요)
        return view
    }



    override fun onDestroy() {
        if(MySharedPreferences.getAutoChecked(requireContext()).equals("N")){
            MySharedPreferences.clearUser(requireContext())
        }
        super.onDestroy()
    }




}