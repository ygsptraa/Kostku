package com.example.kostku

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.kostku.databinding.ActivityDetailBinding
import com.example.kostku.databinding.ActivityLoginBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {

    private lateinit var tvNama: TextView
    private lateinit var tvAlamat : TextView
    private lateinit var tvImage : ImageView
    private lateinit var tvHarga : TextView
    private lateinit var tvKategori : TextView
    private lateinit var tvGmaps : TextView
    private lateinit var firebaseRef : DatabaseReference
//    private lateinit var binding: ActivityDetailBinding
//    private lateinit var btnGmpas: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initView()
        setValuesToViews()

        val database = FirebaseDatabase.getInstance()
        firebaseRef = database.getReference("Kost")

    }
    private fun initView() {
        tvNama = findViewById(R.id.tvNama)
        tvAlamat = findViewById(R.id.tvAlamat)
        tvImage = findViewById(R.id.tvImage)
        tvHarga = findViewById(R.id.tvHarga)
//        btnGmpas = findViewById(R.id.btnLokasi)
        tvGmaps = findViewById(R.id.tvGmaps)
        tvKategori = findViewById(R.id.tvKategori)

         }
    private fun setValuesToViews(){
        tvNama.text = intent.getStringExtra("nama")
        tvAlamat.text = intent.getStringExtra("alamat")
        tvHarga.text = intent.getStringExtra("harga")
        val imageUrl = intent.getStringExtra("imgUrl")
        tvKategori.text = intent.getStringExtra("kategori")
        tvGmaps.text = intent.getStringExtra("gmaps")
        Glide.with(this).load(imageUrl).into(tvImage)
//        binding.btnLokasi.setOnClickListener {
//            val lokasi = Intent(Intent.ACTION_SENDTO, Uri.parse("gmaps"))
//            startActivity(Intent.createChooser(lokasi,"gmaps"))
        }
    }
