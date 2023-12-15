package com.example.sqlitepracticaroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserListAdapter(private val dataList: List<Pair<String, String>>) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewColumn1: TextView = view.findViewById(R.id.textViewColumn1)
        val textViewColumn2: TextView = view.findViewById(R.id.textViewColumn2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)

        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = layoutParams

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.textViewColumn1.text = item.first
        holder.textViewColumn2.text = item.second
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}