package com.example.matchcubeandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.matchcubeandroid.fragments.Matchtabplayer
import com.example.matchcubeandroid.fragments.Matchtabteam

/*Match Tab 선수, 팀 탭 레이아웃 어댑터*/
public class MatchTabTeamPlrAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!) {

    private var fragmentList: MutableList<Fragment> = arrayListOf()
    private var titleList : MutableList<String> = arrayListOf()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun addFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        titleList.add(title)
    }
}