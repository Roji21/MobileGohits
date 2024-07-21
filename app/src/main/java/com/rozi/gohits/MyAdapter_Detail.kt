package com.rozi.gohits

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter_Detail(private var menuItems: List<Menu_perserta>) : RecyclerView.Adapter<MyAdapter_Detail.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.content_participant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.titleTextView.text = menuItem.nama
        Log.d("MyAdapter_Detail", "Binding item at position $position with name ${menuItem.nama}")
    }

    override fun getItemCount(): Int {
        Log.d("MyAdapter_Detail", "Total items: ${menuItems.size}")
        return menuItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    }
}
