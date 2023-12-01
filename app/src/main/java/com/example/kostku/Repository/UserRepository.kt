package com.example.kostku.Repository

import androidx.lifecycle.MutableLiveData
import com.example.kostku.Model.Kost
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepository {

    private val databaseReference :  DatabaseReference = FirebaseDatabase.getInstance().getReference("kost")

    @Volatile private var INSTANCE : UserRepository ?= null

    fun getInstance() : UserRepository {
        return INSTANCE ?: synchronized(this){

            val instance = UserRepository()
            INSTANCE = instance
            instance
        }
    }
    fun loadKost(kostList : MutableLiveData<List<Kost>>){
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val _kostList : List<Kost> = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Kost::class.java)!!
                    }
                    kostList.postValue(_kostList)
                }catch (e : Exception){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}