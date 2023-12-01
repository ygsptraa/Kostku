package com.example.kostku.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kostku.Model.Kost
import com.example.kostku.R

class Adapter(private val kostList: List<Kost>) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item, parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return kostList.size
    }
//    fun updateKostList(kostList: List<Kost>){
//        this.kostList.clear()
//        this.kostList.addAll(kostList)
//        notifyDataSetChanged()
//    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = kostList[position]

        holder.namaKost.text = currentItem.namaKos
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val namaKost : TextView = itemView.findViewById(R.id.tvNamaKos)
    }
}
//class Adapter(private val dataList: List<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
//
//    // ViewHolder untuk menyimpan referensi ke elemen tampilan
//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val textView: TextView = itemView.findViewById(R.id.tvNamaKos)
//    }
//
//    // Membuat ViewHolder baru
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
//        return MyViewHolder(itemView)
//    }
//
//    // Menghubungkan data dengan elemen dalam ViewHolder
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currentItem = dataList[position]
//        holder.textView.text = currentItem
//    }
//
//    // Mengembalikan jumlah total item dalam data set
//    override fun getItemCount(): Int {
//        return dataList.size
//    }
//}