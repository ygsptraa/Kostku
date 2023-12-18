package com.example.kostku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var tvNama: TextView
    private lateinit var tvAlamat : TextView
    private lateinit var tvImage : ImageView
    private lateinit var tvHarga : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initView()
        setValuesToViews()
    }
    private fun initView() {
        tvNama = findViewById(R.id.tvNama)
        tvAlamat = findViewById(R.id.tvAlamat)
        tvImage = findViewById(R.id.tvImage)
        tvHarga = findViewById(R.id.tvHarga)
         }
    private fun setValuesToViews(){
        tvNama.text = intent.getStringExtra("nama")
        tvAlamat.text = intent.getStringExtra("alamat")
        tvHarga.text = intent.getStringExtra("harga")
        val imageUrl = intent.getStringExtra("imgUrl")
        Glide.with(this).load(imageUrl).into(tvImage)

    }

}