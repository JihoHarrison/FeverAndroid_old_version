package com.example.matchcubeandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.matchcubeandroid.fragments.Matchtabplayer
import com.example.matchcubeandroid.fragments.Matchtabteam

class MatchTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1->Matchtabplayer()
            else->Matchtabteam()
        }
    }

}