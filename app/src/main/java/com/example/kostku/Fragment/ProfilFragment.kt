package com.example.kostku.Fragment

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.kostku.LoginActivity
import com.example.kostku.Model.User
import com.example.kostku.R
import com.example.kostku.databinding.FragmentProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.UUID

class ProfilFragment : Fragment() {
    private lateinit var binding: FragmentProfilBinding
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var userImageProfile: ImageView
    private lateinit var etUserName: EditText
    private lateinit var btnSave: Button
    private lateinit var btnAjukanKost: TextView
    private var filePath: Uri? = null
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            if (data != null) {
                filePath = data.data
                handleImageSelection()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid)
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)

                if (user != null) {
                    etUserName = binding.etUserName
                    etUserName.setText(user.userName)

                    if (user.profileImage == "") {
                        userImageProfile.setImageResource(R.drawable.profile_image)
                    } else {
                        Glide.with(requireContext()).load(user.profileImage).into(binding.userImageProfile)
                    }
                } else {
                    Log.e("ProfilFragment", "User data is null")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ProfilFragment", "Error fetching user data: ${error.message}")
            }
        })

        btnSave = binding.btnSave
        userImageProfile = binding.userImageProfile
        btnAjukanKost = binding.btnAjukanKost

        userImageProfile.setOnClickListener {
            chooseImage()
        }

        btnSave.setOnClickListener {
            uploadImage()
            binding.progressBar.visibility = View.VISIBLE
        }


        btnAjukanKost.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "kelompok73a@gmail.com", null))
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }



        return binding.root
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        getContent.launch(intent)
    }

    private fun handleImageSelection() {
        try {
            if (filePath != null) {
                val source = ImageDecoder.createSource(requireContext().contentResolver, filePath!!)
                val bitmap = ImageDecoder.decodeBitmap(source)

                binding.userImageProfile.setImageBitmap(bitmap)
                binding.btnSave.visibility = View.VISIBLE
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }



    private fun uploadImage() {
        if (filePath != null) {
            val ref: StorageReference = storageRef.child("image/" + UUID.randomUUID().toString())
            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    binding.progressBar.visibility = View.GONE
                    val hashMap: HashMap<String, Any> = HashMap()
                    hashMap["userName"] = binding.etUserName.text.toString()
                    hashMap["profileImage"] = filePath.toString()
                    databaseReference.updateChildren(hashMap)
                    binding.btnSave.visibility = View.GONE
                    Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        view.findViewById<Button>(R.id.btnSignOut).setOnClickListener {
            auth.signOut()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
