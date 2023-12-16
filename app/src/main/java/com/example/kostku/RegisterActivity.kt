package com.example.kostku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.kostku.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        //Menambahkan clicklistener pada button login
        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnSign.setOnClickListener {
            val email = binding.editEmailRegister.text.toString()
            val password = binding.editPasswordRegister.text.toString()
            val userName = binding.editNameRegister.text.toString()

            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(applicationContext, "Username harus diisi", Toast.LENGTH_SHORT)
                    .show()
            }
            //Validasi email
            if (email.isEmpty()){
                binding.editEmailRegister.error = "Email Harus diisi"
                binding.editEmailRegister.requestFocus()
                return@setOnClickListener
            }
            //validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.editEmailRegister.error = "Email tidak valid"
                binding.editPasswordRegister.requestFocus()
                return@setOnClickListener
            }
            //Validasi password
            if(password.isEmpty()) {
                binding.editPasswordRegister.error = "Password Harus Diisi"
                binding.editPasswordRegister.requestFocus()
                return@setOnClickListener
            }
            //validasi password
            if(password.length < 6){
                binding.editPasswordRegister.error = "Password Minimal 6 Karakter"
                binding.editPasswordRegister.requestFocus()
                return@setOnClickListener
            }
            RegisterFirebase(userName,email,password)
        }
    }

    //melakukan registrasi pengguna dengan Firebase Authentication, dan memberikan umpan balik kepada pengguna apakah registrasi berhasil atau jika terjadi kesalahan.
    private fun RegisterFirebase(userName:String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    val user: FirebaseUser? = auth.currentUser
                    val userId:String = user!!.uid

                    databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId)

                    val hashMap:HashMap<String,String> = HashMap()
                    hashMap.put("userId",userId)
                    hashMap.put("userName",userName)
                    hashMap.put("profileImage","")

                    databaseReference.setValue(hashMap).addOnCompleteListener(this){
                        if (it.isSuccessful){
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
    }
}