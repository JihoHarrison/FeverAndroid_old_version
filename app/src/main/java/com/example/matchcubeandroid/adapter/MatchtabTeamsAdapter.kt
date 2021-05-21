package com.example.matchcubeandroid.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.image.URLtoBitmapTask
import com.example.matchcubeandroid.model.MatchtabTeamsModel
import com.example.matchcubeandroid.model.ProfileModel
import de.hdodenhof.circleimageview.CircleImageView
import java.net.URL

class MatchtabTeamsAdapter (val context: Context, val teamsLists: ArrayList<MatchtabTeamsModel>) : RecyclerView.Adapter<MatchtabTeamsAdapter.TeamViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchtabTeamsAdapter.TeamViewHolder { // item xml 뷰들을 끌어와서 adapter에 붙여준다
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_team_list_item, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchtabTeamsAdapter.TeamViewHolder, position: Int) { //  실제로 호출 시 onBindViewHolder가 지속적으로 호출됨
//        holder.imgTeams.setImageResource(teamsLists.get(position).imgTeams)
        Log.d("KAKA", "jt : " + teamsLists.get(position).imgTeams)
//        Glide.with(context).load(teamsLists.get(position).imgTeams).into(holder.imgTeams)
        Glide.with(context).load("https://match-cube.s3.ap-northeast-2.amazonaws.com/account/8166635a-9fde-432c-9650-60ff40910de6.png").into(holder.imgTeams)

        holder.txtTeamNameLists.text = teamsLists.get(position).txtTeamNameList
        holder.txtOneLineIntro.text = teamsLists.get(position).txtOneLineIntro
    }

    override fun getItemCount(): Int {
        return teamsLists.size
    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { // 뷰 홀더 클래스
        val imgTeams = itemView.findViewById<ImageView>(R.id.imgTeams)
        val txtTeamNameLists = itemView.findViewById<TextView>(R.id.txtTeamNameLists)
        val txtOneLineIntro = itemView.findViewById<TextView>(R.id.txtOneLineIntro)
    }
}