package com.example.mytoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.mytoproject.auth.introActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        if (auth.currentUser?.uid == null) {
            Handler(Looper.getMainLooper()).postDelayed({

                startActivity(Intent(this, introActivity::class.java))
                finish()
            }, 3000)
            Log.d("SplashActivity","null")
        } else {
            Handler(Looper.getMainLooper()).postDelayed({

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 3000)
            Log.d("SplashActivity","not null")
        }


    }
}