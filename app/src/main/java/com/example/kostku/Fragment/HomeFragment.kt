package com.example.kostku.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.kostku.Adapter.Adapter
import com.example.kostku.Model.UserViewModel
import com.example.kostku.R

private lateinit var viewModel : UserViewModel
private lateinit var userRecyclerView : RecyclerView
lateinit var adapter: Adapter

class HomeFragment : Fragment() {
    val imageList = ArrayList<SlideModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Add images to the imageList
        imageList.add(SlideModel("https://bit.ly/2YoJ77H"))
        imageList.add(SlideModel("https://bit.ly/2BteuF2"))
        imageList.add(SlideModel("https://bit.ly/3fLJf72"))

        // Set up the ImageSlider
        val imageSlider = view.findViewById<ImageSlider>(R.id.sliderlayout)
        imageSlider.setImageList(imageList)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRecyclerView = view.findViewById(R.id.recyclerView)
        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.setHasFixedSize(true)
        adapter = Adapter()
        userRecyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        viewModel.allKost.observe(viewLifecycleOwner, {
            adapter.updateKostList(it)
        })
    }
}