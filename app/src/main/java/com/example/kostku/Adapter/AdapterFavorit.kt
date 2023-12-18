package com.example.kostku.Adapter

import android.view.LayoutInflater
import android.view.View
import android.R
import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kostku.Model.FavoritModel
import com.example.kostku.databinding.ItemFavoritBinding
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class AdapterFavorit(val list: List<FavoritModel>,val database: FirebaseDatabase):RecyclerView.Adapter<AdapterFavorit.FavoritViewHolder>() {

    class FavoritViewHolder(val binding: ItemFavoritBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritViewHolder {
        return FavoritViewHolder(
            ItemFavoritBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: FavoritViewHolder, position: Int) {
        val bindpos = list[position]
        holder.apply {
            binding.apply {
                tvNamaFavorit.text = bindpos.namaKost
                tvHarga.text = bindpos.harga
                Picasso.get().load(bindpos.imgUrl).into(tvImage)


                btnTest.setOnClickListener {
                    database.getReference("favorit").child(bindpos.id).removeValue()
                }


            }

        }
    }
}