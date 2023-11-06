package com.example.kostku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.kostku.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        //Menambahkan clicklistener untuk register
        binding.TvToSign.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        //menambahkan OnclickListener pada button login
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.editUsernameLogin.text.toString()
            val password = binding.editPasswordLogin.text.toString()

            //Validasi email
            if (email.isEmpty()){
                binding.editUsernameLogin.error = "Email Harus diisi"
                binding.editUsernameLogin.requestFocus()
                return@setOnClickListener
            }
            //validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.editUsernameLogin.error = "Email tidak valid"
                binding.editPasswordLogin.requestFocus()
                return@setOnClickListener
            }
            //Validasi password
            if(password.isEmpty()) {
                binding.editPasswordLogin.error = "Password Harus Diisi"
                binding.editPasswordLogin.requestFocus()
                return@setOnClickListener
            }
            LoginFirebase(email,password,)
        }

    }
    //Melakukan login pengguna dengan menggunakan email dan password yang ada di database
    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this,"Selamat Datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Email atau Password anda salah", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
