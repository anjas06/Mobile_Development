package com.anjaslp.ailoop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.anjaslp.ailoop.home.HomeActivity
import com.anjaslp.ailoop.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var textFullName: TextView
    private lateinit var textEmail: TextView
    private lateinit var btnLogout: Button

    val firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        textFullName = findViewById(R.id.tvName)
        textEmail = findViewById(R.id.tvEmail)
        btnLogout = findViewById(R.id.btLogout)

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser!=null){
            textFullName.text = firebaseUser.displayName
            textEmail.text = firebaseUser.email
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        btnLogout.setOnClickListener{
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        setupBottomAppBar()
    }

    private fun setupBottomAppBar() {
        val btnHome = findViewById<LinearLayout>(R.id.btnHome)
        val btnCamera = findViewById<LinearLayout>(R.id.btnCamera)

        btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        btnCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}