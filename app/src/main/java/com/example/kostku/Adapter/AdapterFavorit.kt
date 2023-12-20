package com.example.kostku.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kostku.Model.FavoritModel
import com.example.kostku.databinding.ItemFavoritBinding
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class AdapterFavorit(val list: List<FavoritModel>,val database: FirebaseDatabase):RecyclerView.Adapter<AdapterFavorit.FavoritViewHolder>() {


    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    class FavoritViewHolder(val binding: ItemFavoritBinding, clickListener: onItemClickListener):RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritViewHolder {
        return FavoritViewHolder(
            ItemFavoritBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            mListener
        )
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: FavoritViewHolder, position: Int) {
        val bindpos = list[position]
        holder.apply {
            binding.apply {
                tvNama.text = bindpos.namaKost
                tvHarga.text = bindpos.harga
                tvKategori.text = bindpos.kategori
                Picasso.get().load(bindpos.imgUrl).into(tvImage)

                buttonFavorit.setOnClickListener {
                    database.getReference("favorit").child(bindpos.id).removeValue()
                }
            }

        }
    }
}