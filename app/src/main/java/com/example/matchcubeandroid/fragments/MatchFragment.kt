package com.example.matchcubeandroid.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.adapter.MatchTabAdapter
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_match.*

class MatchFragment : Fragment() {

    private val tabTextList = arrayListOf("선수","팀")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
//        init()



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter by lazy { MatchTabAdapter(this) }
        matchTabViewPager.adapter = adapter
        TabLayoutMediator(matchTabLayout, matchTabViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "팀"
                1 -> tab.text = "선수"

            }
        }.attach()


    }

//    private fun init(){
//        matchTabViewPager.adapter = MatchTabAdapter(this)
//        TabLayoutMediator(matchTabLayout, matchTabViewPager){
//                tab, position ->
//            tab.text = tabTextList[position]
//        }.attach()
//    }

    override fun onDestroy() {
        if(MySharedPreferences.getAutoChecked(requireContext()).equals("N")){
            MySharedPreferences.clearUser(requireContext())
        }
        super.onDestroy()
    }


}