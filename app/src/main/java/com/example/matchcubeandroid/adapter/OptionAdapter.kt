package com.example.matchcubeandroid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.model.OptionModel

class OptionAdapter (val context: Context, val OptionList: ArrayList<OptionModel>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.option_list_item, null)

        val opstxt: TextView = view.findViewById(R.id.optiontxt)

        val user = OptionList[position]

        opstxt.text = user.txtOptionList

        return view
    }

    override fun getItem(position: Int): Any {
        return OptionList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return OptionList.size
    }


}