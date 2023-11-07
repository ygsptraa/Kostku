package com.example.kostku.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kostku.Model.User
import com.example.kostku.R

class Adapter : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    private val kostList = ArrayList<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item, parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return kostList.size
    }
    fun updateKostList(kostList: List<User>){
        this.kostList.clear()
        this.kostList.addAll(kostList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = kostList[position]

        holder.namaKost.text = currentItem.namaKos
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val namaKost : TextView = itemView.findViewById(R.id.tvNamaKos)
    }
}