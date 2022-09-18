package com.example.mytoproject.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.mytoproject.R
import com.example.mytoproject.comment.CommentLVAdapter
import com.example.mytoproject.comment.CommentModel
import com.example.mytoproject.databinding.ActivityBoardInsideBinding
import com.example.mytoproject.utils.FBAuth
import com.example.mytoproject.utils.FBRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class BoardInsideActivity : AppCompatActivity() {

    private val TAG = BoardInsideActivity::class.java
    private lateinit var binding: ActivityBoardInsideBinding
    private lateinit var key: String
    private lateinit var comment_key: String
    private val commentDataLIST = mutableListOf<CommentModel>()
    private val idList = mutableListOf<String>()
    private val new_idList = mutableListOf<String>()

    private lateinit var commenAdapter : CommentLVAdapter
    //private lateinit var time : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_inside)
        binding.boardSettingIcon.setOnClickListener {
            showDialog()

        }
        //방법 1
//        var title = intent.getStringExtra("title").toString()
//        var content = intent.getStringExtra("content").toString()
//        var time = intent.getStringExtra("time").toString()
//
//        binding.titleArea.text = title
//        binding.contentArea.text = content
//        binding.timeArea.text = time



        //방법2
        key = intent.getStringExtra("key").toString()
        getBoardData(key)
        getImageData(key)

        binding.commentIV.setOnClickListener {
            insertCommet(key)
        }
        getcommentData(key)


        //어댑터랑 리스트뷰랑 연결.
        commenAdapter = CommentLVAdapter(commentDataLIST)
        binding.commentLV.adapter = commenAdapter








    }

    fun insertCommet(key: String) {


        FBRef.commentRef.child(key).push()
            .setValue(
                CommentModel(binding.commentArea.text.toString(),FBAuth.getTime())
            )
        Toast.makeText(this,"댓글을 입력하였습니다",Toast.LENGTH_SHORT).show()
        binding.commentArea.setText("")


    }
    private fun getcommentData(key:String) {
        val postListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                commentDataLIST.clear()

                for(dataModel in snapshot.children) {
                    val item = dataModel.getValue(CommentModel::class.java)
                    commentDataLIST.add(item!!)

                }
                //어댑터 동기화 시키기
                commenAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)

    }

    private fun getBoardData(key: String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val dataModel = snapshot.getValue(BoardModel::class.java)



                binding.titleArea.text = dataModel!!.title
                binding.contentArea.text = dataModel!!.content
                binding.timeArea.text = dataModel!!.time




            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)


    }

    private fun getImageData(key: String) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key + ".png")

// ImageView in your Activity
        val imageViewFromFB = binding.getImageArea

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(imageViewFromFB)
            } else {
                imageViewFromFB.isVisible = false
            }

        })
    }

    private fun showDialog() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editBtn)?.setOnClickListener {
            val intent = Intent(this, boardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)

        }
        alertDialog.findViewById<Button>(R.id.deleteBtn)?.setOnClickListener {
            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_SHORT).show()
            finish()


        }

    }
//    private fun Check_New_Posting() {
//        if (time + )
//    }
    private fun make_Anonymous() :String {

    var uid = intent.getStringExtra("uid").toString()


    for(i in 0 until idList.size) {
        if(uid[i] !=null) {
            if(idList[i] != uid) {
                idList.add(uid)
            }
        }
        else {
            idList.add(uid)

        }

    }
    for(i in 0 until idList.size) {
        new_idList.add("익명+${i+1}")
    }



    return new_idList.toString()

    }
}