package com.example.kostku.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kostku.Model.Kost
import com.example.kostku.databinding.KostItemBinding
import com.squareup.picasso.Picasso

class Adapter(private val kostList : java.util.ArrayList<Kost>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(val binding: KostItemBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(KostItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return kostList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = kostList[position]
        holder.apply {
            binding.apply {
                tvNama.text = currentItem.nama
                tvAlamat.text = currentItem.alamat
                tvId.text = currentItem.id
                Picasso.get().load(currentItem.imgUrl).into(tvImage)
            }
        }
    }
}
