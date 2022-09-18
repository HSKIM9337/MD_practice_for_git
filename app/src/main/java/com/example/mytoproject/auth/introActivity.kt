package com.example.mytoproject.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mytoproject.MainActivity
import com.example.mytoproject.R
import com.example.mytoproject.databinding.ActivityIntroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class introActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_intro)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_intro)
        auth = Firebase.auth

        binding.loginBtn.setOnClickListener() {

            val intent = Intent(this,LoginActivity::class.java)
            startActivity((intent))

        }

        binding.joinBtn.setOnClickListener() {
            val intent = Intent(this,JoinActivity::class.java)
            startActivity((intent))
        }

        binding.noAccountBtn.setOnClickListener() {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "로그인에 성공하였습니다", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                       Toast.makeText(this,"로그인에 실패하였습니다",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}