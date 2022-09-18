package com.example.mytoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.mytoproject.auth.introActivity
import com.example.mytoproject.setting.SettingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.settingBtn).setOnClickListener{
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }

        auth = Firebase.auth
       /* findViewById<Button>(R.id.logoutBtn).setOnClickListener {

            auth.signOut()
            val intent = Intent(this,introActivity::class.java)
            startActivity(intent)
            finish()
        }*/
    }
}