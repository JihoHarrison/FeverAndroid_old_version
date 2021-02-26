package com.example.matchcubeandroid.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.login.LoginActivity
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.adapter.ProfileAdapter
import com.example.matchcubeandroid.model.ProfileModel
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_page.*

class MyPageFragment : Fragment(), View.OnClickListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_my_page, container, false)
        val btn: Button = view.findViewById(R.id.logoutbt)
        val rvTeamProfImgs: RecyclerView = view.findViewById(R.id.rvTeamProfImgs)
        btn.setOnClickListener(this)
        val profileList = arrayListOf( // SharedPreference에 저장된 값으로 수정 가능 할 것으로 보임!
            ProfileModel(R.drawable.ic_android_drawer, "토트넘"),
            ProfileModel(R.drawable.ic_android_drawer, "함부르크"),
            ProfileModel(R.drawable.ic_android_drawer, "레버쿠젠"),
            ProfileModel(R.drawable.ic_android_drawer, "레알마드리드"),
            ProfileModel(R.drawable.ic_android_drawer, "바르셀로나"),
            ProfileModel(R.drawable.ic_android_drawer, "AT마드리드"),
            ProfileModel(R.drawable.ic_android_drawer, "바이에른 뮌헨"),
            ProfileModel(R.drawable.ic_android_drawer, "FC 서울"),
            ProfileModel(R.drawable.ic_android_drawer, "FC 매치큐브")
        )

        var context: Context = view.context

        rvTeamProfImgs.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvTeamProfImgs.setHasFixedSize(true)
        rvTeamProfImgs.adapter = ProfileAdapter(profileList)

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