package com.example.mytoproject.board

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.mytoproject.R
import com.example.mytoproject.databinding.ActivityBoardWriteBinding
import com.example.mytoproject.databinding.ActivityIntroBinding
import com.example.mytoproject.utils.FBAuth
import com.example.mytoproject.utils.FBRef
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class BoardWriteActivity : AppCompatActivity() {

    private var timestamp = System.currentTimeMillis()
    private lateinit var binding: ActivityBoardWriteBinding
    private var IsImageUploaded = false







    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {

                binding.imageArea.setImageURI(it)

            }
        )


        binding.writeBtn.setOnClickListener {


            val title = binding.titleArea.text.toString()
            val contents = binding.contentsArea.text.toString()
            val uid = FBAuth.getUid()
            val time = FBAuth.getTime()
            val key = FBRef.boardRef.push().key.toString()






            FBRef.boardRef
                .child(key)
                .setValue(BoardModel(title, contents, uid, time, timestamp.toString()))


            Toast.makeText(this, "게시글을 썼습니다", Toast.LENGTH_SHORT).show()

            if(IsImageUploaded) {
                imageUpload(key)
            }
            finish()




        }




        binding.imageArea.setOnClickListener {

            getImage.launch("image/*")
            IsImageUploaded = true
        }


    }
    private fun convertTimestampToDate(timestamp: Long) {
        val sdf = SimpleDateFormat("yyyy-MM-dd-hh-mm")
        val date = sdf.format(timestamp)
        Log.d("TTT UNix Date -> ", sdf.format((System.currentTimeMillis())).toString())
        Log.d("TTTT date -> ", date.toString())


    }





    private fun imageUpload(key:String) {

        val storage = Firebase.storage
        val imageView = binding.imageArea
        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key+".png")

        // Get the data from an ImageView as bytes
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

    }
}