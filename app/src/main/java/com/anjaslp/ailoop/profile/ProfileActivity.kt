package com.anjaslp.ailoop.profile

import ProfileViewModel
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anjaslp.ailoop.camera.CameraActivity
import com.anjaslp.ailoop.MainActivity
import com.anjaslp.ailoop.R
import com.anjaslp.ailoop.home.HomeActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var textFullName: TextView
    private lateinit var textEmail: TextView
    private lateinit var btnLogout: Button
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var progressDialog: ProgressDialog

    private var backButtonPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("Silahkan tunggu...")

        textFullName = findViewById(R.id.tvName)
        textEmail = findViewById(R.id.tvEmail)
        btnLogout = findViewById(R.id.btLogout)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileViewModel.userFullName.observe(this) { fullName ->
            textFullName.text = fullName
        }

        profileViewModel.userEmail.observe(this) { email ->
            textEmail.text = email
        }

        profileViewModel.logoutResult.observe(this) { loggedOut ->
            if (loggedOut) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        profileViewModel.getUserInfo()

        btnLogout.setOnClickListener {
            progressDialog.show()
            profileViewModel.logout()
        }

        setupBottomAppBar()
    }

    override fun onBackPressed() {
        if (backButtonPressedOnce) {
            super.onBackPressed()
            finishAffinity()
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
            backButtonPressedOnce = true

            android.os.Handler().postDelayed({
                backButtonPressedOnce = false
            }, 2000)
        }
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
