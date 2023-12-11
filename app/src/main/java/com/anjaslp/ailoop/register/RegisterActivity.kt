package com.anjaslp.ailoop.register

import RegisterViewModel
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anjaslp.ailoop.databinding.ActivityRegisterBinding
import com.anjaslp.ailoop.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var registerViewModel: RegisterViewModel

    override fun onStart() {
        super.onStart()
        registerViewModel.checkUserLoggedIn()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("Silahkan tunggu...")

        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        registerViewModel.registrationResult.observe(this, { registered ->
            if (registered) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        })

        registerViewModel.errorMessage.observe(this, { error ->
            progressDialog.dismiss()
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })

        binding.btSignup.setOnClickListener {
            if (binding.editName.text.isNotEmpty()
                && binding.editEmail.text.isNotEmpty()
                && binding.editPassword.text.isNotEmpty()
            ) {
                val fullName = binding.editName.text.toString()
                val email = binding.editEmail.text.toString()
                val password = binding.editPassword.text.toString()
                registerViewModel.register(fullName, email, password)
                progressDialog.show()
            } else {
                Toast.makeText(this, "Silahkan isi dulu semua data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
