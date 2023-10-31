package com.example.kostku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.kostku.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSign.setOnClickListener {
            val email = binding.editEmailRegister.text.toString()
            val password = binding.editPasswordRegister.text.toString()
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
            RegisterFirebase(email,password)
        }
    }
}

