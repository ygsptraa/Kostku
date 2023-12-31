package com.example.kostku.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kostku.Model.FavoritModel
import com.example.kostku.Model.Kost
import com.example.kostku.databinding.KostItemBinding
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso

class Adapter(private var originalKostList: ArrayList<Kost>, val database: DatabaseReference) : RecyclerView.Adapter<Adapter.ViewHolder>() {

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
        return originalKostList.size
    }

    fun searchDataList(searchList: ArrayList<Kost>) {
        originalKostList = searchList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = originalKostList[position]
        holder.apply {
            binding.apply {
                tvNama.text = currentItem.nama
                // tvAlamat.text = currentItem.alamat
                tvHarga.text = currentItem.harga
                tvKategori.text = currentItem.kategori
                Picasso.get().load(currentItem.imgUrl).into(tvImage)

                buttonFavorit.setOnClickListener {
                    val id = database.push().key!!
                    val data = FavoritModel(id, currentItem.nama!!, currentItem.harga!!, currentItem.imgUrl!!, currentItem.alamat!!, currentItem.kategori!!, currentItem.gmaps!!)

                    database.child(id).setValue(data).addOnSuccessListener {

                    }.addOnFailureListener {

                    }

                }
            }

        }
    }

    fun getOriginalPosition(position: Int): Int {
        return position
    }
}

