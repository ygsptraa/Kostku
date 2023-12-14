package com.example.kostku.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.kostku.LoginActivity
import com.example.kostku.R
import com.example.kostku.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
class ProfilFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profil, container, false)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





//Kode LAwas
//class ProfilFragment : Fragment() {
//
//    companion object{
//        const val REQUEST_CAMERA = 100
//    }
//    private lateinit var imageUri: Uri
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_profil, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<ImageView>(R.id.ivProfile).setOnClickListener {
//            intentCamera()
//        }
//    }
//
//    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == RESULT_OK) {
//            val data: Intent? = result.data
//            if (data != null) {
//                val imgBitmap = data.extras?.get("data") as Bitmap
//                uploadImage(imgBitmap)
//            }
//        }
//    }
//
//    private fun intentCamera() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
//            activity?.packageManager?.let {
//                intent.resolveActivity(it)?.let {
//                    takePictureLauncher.launch(intent)
//                }
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK){
//            val imgBitmap = data?.extras?.get("data") as Bitmap
//            uploadImage(imgBitmap)
//        }
//    }
//
//    private fun uploadImage(imgBitmap: Bitmap) {
//    val baos =  ByteArrayOutputStream()
//    val ref :StorageReference =FirebaseStorage.getInstance().reference.child("img/${FirebaseAuth.getInstance().currentUser?.uid}")
//
//    imgBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
//        val image:ByteArray = baos.toByteArray()
//
//        ref.putBytes(image)
//            .addOnCompleteListener{
//                if (it.isSuccessful){
//                    ref.downloadUrl.addOnCompleteListener{
//                        it.result?.let {
//                            imageUri = it
//                            view?.findViewById<CircleImageView>(R.id.ivProfile)?.setImageBitmap(imgBitmap)
//                        }
//                    }
//                }
//            }
//    }
//}