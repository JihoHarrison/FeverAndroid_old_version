package com.example.matchcubeandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.createBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.model.MatchtabTeamsModel
import com.example.matchcubeandroid.model.ProfileModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.match_team_list_item.view.*

class MatchtabTeamsAdapter (private val context: Context) : RecyclerView.Adapter<MatchtabTeamsAdapter.TeamViewHolder>(){

    var teamsLists = ArrayList<MatchtabTeamsModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchtabTeamsAdapter.TeamViewHolder { // item xml 뷰들을 끌어와서 adapter에 붙여준다
        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_team_list_item, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchtabTeamsAdapter.TeamViewHolder, position: Int) { //  실제로 호출 시 onBindViewHolder가 지속적으로 호출됨
        holder.bind(teamsLists[position])
    }

    override fun getItemCount(): Int {
        return teamsLists.size
    }

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { // 뷰 홀더 클래스

        val imgTeams = itemView.findViewById<ImageView>(R.id.imgTeams)
        val txtTeamNameLists = itemView.findViewById<TextView>(R.id.txtTeamNameLists)
        val txtOneLineIntro = itemView.findViewById<TextView>(R.id.txtOneLineIntro)

        fun bind(item: MatchtabTeamsModel){
            txtTeamNameLists.text = item.txtTeamNameList
            txtOneLineIntro.text = item.txtOneLineIntro
            Glide.with(itemView).load(item.imgTeams).into(imgTeams)
        }

    }
}