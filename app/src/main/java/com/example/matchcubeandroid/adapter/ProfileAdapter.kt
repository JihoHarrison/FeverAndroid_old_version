package com.example.matchcubeandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.model.ProfileModel
import de.hdodenhof.circleimageview.CircleImageView

class ProfileAdapter (val profileLists: ArrayList<ProfileModel>) : RecyclerView.Adapter<ProfileAdapter.TeamViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.TeamViewHolder { // item xml 뷰들을 끌어와서 adapter에 붙여준다
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_list_item, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileAdapter.TeamViewHolder, position: Int) { //  실제로 호출 시 onBindViewHolder가 지속적으로 호출됨
        holder.imgTeams.setImageResource(profileLists.get(position).imgTeams)
        holder.txtTeamNameLists.text = profileLists.get(position).txtTeamNameList
    }

    override fun getItemCount(): Int {
        return profileLists.size
    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { // 뷰 홀더 클래스
        val imgTeams = itemView.findViewById<ImageView>(R.id.imgTeams)
        val txtTeamNameLists = itemView.findViewById<TextView>(R.id.txtTeamNameLists)
    }
}