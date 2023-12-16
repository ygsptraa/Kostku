package com.example.kostku.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kostku.Model.Kost
import com.example.kostku.databinding.KostItemBinding
import com.squareup.picasso.Picasso

class Adapter(private var kostList: ArrayList<Kost>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    class ViewHolder(val binding: KostItemBinding, clickListener: onItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            KostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            mListener
        )
    }

    override fun getItemCount(): Int {
        return kostList.size
    }

    fun searchDataList(searchList: ArrayList<Kost>) {
        kostList = searchList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = kostList[position]
        holder.apply {
            binding.apply {
                tvNama.text = currentItem.nama
                tvAlamat.text = currentItem.alamat
                Picasso.get().load(currentItem.imgUrl).into(tvImage)
            }
        }
    }
}
