package com.anjaslp.ailoop.camera

import CameraViewModel
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.anjaslp.ailoop.R
import com.anjaslp.ailoop.databinding.ActivityCameraBinding
import com.anjaslp.ailoop.home.HomeActivity
import com.anjaslp.ailoop.profile.ProfileActivity

class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var cameraViewModel: CameraViewModel
    private var backButtonPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraViewModel = ViewModelProvider(this).get(CameraViewModel::class.java)

        cameraViewModel.cameraPermissionGranted.observe(this) { permissionGranted ->
            binding.btTake.isEnabled = permissionGranted
            binding.btGallery.isEnabled = permissionGranted
        }

        cameraViewModel.imageResult.observe(this) { imageBitmap ->
            binding.imageResult.setImageBitmap(imageBitmap)
        }

        binding.btTake.setOnClickListener {
            cameraViewModel.takePhoto(this)
        }

        binding.btGallery.setOnClickListener {
            cameraViewModel.pickFromGallery(this)
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

    override fun onStart() {
        super.onStart()
        cameraViewModel.checkCameraPermission(this)
        requestPermissions()
    }

    private fun requestPermissions() {
        val cameraPermission =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        val storagePermission =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

        if (!cameraPermission || !storagePermission) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                111
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111) {
            cameraViewModel.checkCameraPermission(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraViewModel.handleActivityResult(requestCode, resultCode, data, this)
    }

    private fun setupBottomAppBar() {
        val btnHome = findViewById<LinearLayout>(R.id.btnHome)
        val btnProfile = findViewById<LinearLayout>(R.id.btnProfile)

        btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}