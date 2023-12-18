package com.example.kostku.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.example.kostku.Adapter.Adapter
import com.example.kostku.DetailActivity
import com.example.kostku.Model.Kost
import com.example.kostku.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var kostList: ArrayList<Kost>
    private lateinit var firebaseRef: DatabaseReference
    private lateinit var firebaseRefFavorit: DatabaseReference
    private lateinit var rvAdapter: Adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("Kost")
        firebaseRefFavorit = FirebaseDatabase.getInstance().getReference("favorit")
        kostList = arrayListOf()

        fetchData()

        binding.recycleview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }

        // Image slider
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://rb.gy/xc7xks"))
        imageList.add(SlideModel("https://rb.gy/daglat"))
        imageList.add(SlideModel("https://rb.gy/ni6mly"))

        binding.sliderlayout.setImageList(imageList)

        binding.btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList(newText)
                return false
            }
        })
        return binding.root
    }

    private fun fetchData() {
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                kostList.clear()
                if (snapshot.exists()) {
                    for (kostSnap in snapshot.children) {
                        val kost = kostSnap.getValue(Kost::class.java)
                        kostList.add(kost!!)
                    }

                    rvAdapter = Adapter(kostList,firebaseRefFavorit)
                    binding.recycleview.adapter = rvAdapter

                    rvAdapter.setOnItemClickListener(object : Adapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(activity, DetailActivity::class.java)
                            intent.putExtra("nama", kostList[position].nama)
                            intent.putExtra("alamat", kostList[position].alamat)
                            intent.putExtra("imgUrl", kostList[position].imgUrl)
                            intent.putExtra("harga", kostList[position].harga)
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

    private fun searchList(text: String?) {
        val searchList = ArrayList<Kost>()
        for (kost in kostList) {
            if (kost.nama?.lowercase()?.contains(text?.lowercase() ?: "") == true) {
                searchList.add(kost)
            }
            if (kost.alamat?.lowercase()?.contains(text?.lowercase() ?: "") == true) {
                searchList.add(kost)
            }
        }
        rvAdapter.searchDataList(searchList)
    }
}
