package com.example.kostku.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    //new
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var kostList: ArrayList<Kost>
    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("Kost")
        kostList = arrayListOf()

        fetchData()

        binding.recycleview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }

        // Image slider
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://bit.ly/2YoJ77H"))
        imageList.add(SlideModel("https://bit.ly/2BteuF2"))
        imageList.add(SlideModel("https://bit.ly/3fLJf72"))

        binding.sliderlayout.setImageList(imageList)

        return binding.root
    }

    private fun fetchData() {
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Tambahkan logging untuk memeriksa nilai snapshot
                Log.d("FirebaseData", "Snapshot: $snapshot")

                kostList.clear()
                if (snapshot.exists()) {
                    for (kostSnap in snapshot.children) {
                        val kost = kostSnap.getValue(Kost::class.java)
                        kostList.add(kost!!)
                    }

                    // Tambahkan logging untuk memeriksa data yang dimasukkan ke list
                    Log.d("FirebaseData", "KostList: $kostList")

                    // Set adapter ke RecyclerView
                    val rvAdapter = Adapter(kostList)
                    binding.recycleview.adapter = rvAdapter

                    rvAdapter.setOnItemClickListener(object : Adapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(activity, DetailActivity::class.java)
                            intent.putExtra("id", kostList[position].id)
                            intent.putExtra("nama", kostList[position].nama)
                            intent.putExtra("alamat", kostList[position].alamat)
                            intent.putExtra("imgUrl", kostList[position].imgUrl)
                            startActivity(intent)
                        }
                    })

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Tambahkan logging untuk memeriksa kesalahan
                Log.e("FirebaseError", "Terjadi error! $error")
            }
        })
    }
}
