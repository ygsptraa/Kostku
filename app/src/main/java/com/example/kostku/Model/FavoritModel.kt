package com.example.kostku.Model

data class FavoritModel(
    val id: String = "",
    val namaKost: String ="",
    val harga: String ="",
    val imgUrl: String = "",
    val alamat : String? = "",
    val kategori : String? = "",
    val gmaps : String? = ""

)
