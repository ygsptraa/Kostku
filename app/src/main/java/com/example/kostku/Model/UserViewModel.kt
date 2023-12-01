package com.example.kostku.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kostku.Repository.UserRepository

class UserViewModel : ViewModel() {

    private val repository : UserRepository
    private val _allKost = MutableLiveData<List<Kost>>()
    val allKost : LiveData<List<Kost>> = _allKost

    init {
        repository = UserRepository().getInstance()
        repository.loadKost(_allKost)
    }
}