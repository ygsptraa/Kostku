package com.example.kostku.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.kostku.Adapter.Adapter
import com.example.kostku.Model.Kost
import com.example.kostku.Model.UserViewModel
import com.example.kostku.R
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var viewModel : UserViewModel
private lateinit var userRecyclerView : RecyclerView
//lateinit var adapter: Adapter

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageSlider: ImageSlider

    // ... rest of your code ...

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        imageSlider = view.findViewById(R.id.sliderlayout)
        recyclerView = view.findViewById(R.id.recycleview)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://bit.ly/2YoJ77H"))
        imageList.add(SlideModel("https://bit.ly/2BteuF2"))
        imageList.add(SlideModel("https://bit.ly/3fLJf72"))

        imageSlider.setImageList(imageList)

        FirebaseApp.initializeApp(requireContext())

        val databaseReference = FirebaseDatabase.getInstance().getReference("kost")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val kostList = mutableListOf<Kost>()
                for (snapshot in dataSnapshot.children) {
                    val namaKost = snapshot.child("namaKost").getValue(String::class.java)
                    val alamatKost = snapshot.child("alamatKost").getValue(String::class.java)

                    // Assuming you have a Kost data class
                    val kost = Kost(namaKost)
                    kostList.add(kost)
                }

                // Set up RecyclerView with an Adapter
                val adapter = Adapter(kostList)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })

        return view
    }
}


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        userRecyclerView = view.findViewById(R.id.recycleview)
//        userRecyclerView.layoutManager = LinearLayoutManager(context)
//        userRecyclerView.setHasFixedSize(true)
//        adapter = Adapter()
//        userRecyclerView.adapter = adapter
//        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//        viewModel.allKost.observe(viewLifecycleOwner, {
//            adapter.updateKostList(it)
//        })
//    }
