package com.example.kostku.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kostku.Adapter.AdapterFavorit
import com.example.kostku.DetailActivity
import com.example.kostku.Model.FavoritModel
import com.example.kostku.databinding.FragmentFavoritBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavoritFragment : Fragment() {

    private lateinit var _binding: FragmentFavoritBinding
    private lateinit var database: FirebaseDatabase


    private lateinit var favoritList: ArrayList<FavoritModel>
    private lateinit var rvAdapter: AdapterFavorit
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance()
        favoritList = arrayListOf()

        fetchData()
        _binding.rVfavorit.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }
        return _binding.root
    }

    fun fetchData(){
        database.getReference("favorit").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                favoritList.clear()
                if (snapshot.exists()) {
                    for (kostSnap in snapshot.children) {
                        val kost = kostSnap.getValue(FavoritModel::class.java)
                        favoritList.add(kost!!)
                    }

                    rvAdapter = AdapterFavorit(favoritList,database)
                    _binding.rVfavorit.adapter = rvAdapter
                    rvAdapter.setOnItemClickListener(object : AdapterFavorit.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(activity, DetailActivity::class.java)
                            intent.putExtra("nama", favoritList[position].namaKost)
                            intent.putExtra("id", favoritList[position].id)
                            intent.putExtra("imgUrl", favoritList[position].imgUrl)
                            intent.putExtra("harga", favoritList[position].harga)
                            intent.putExtra("alamat", favoritList[position].alamat)
                            intent.putExtra("kategori", favoritList[position].kategori)
                            intent.putExtra("gmaps", favoritList[position].gmaps)
                            startActivity(intent)
                        }
                    })


                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Terjadi error! $error")
            }
        })
    }


}