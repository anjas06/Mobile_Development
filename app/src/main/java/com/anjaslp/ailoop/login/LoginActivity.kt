package com.anjaslp.ailoop.login

import LoginViewModel
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.ViewModelProvider
import com.anjaslp.ailoop.R
import com.anjaslp.ailoop.databinding.ActivityLoginBinding
import com.anjaslp.ailoop.databinding.ActivityMainBinding
import com.anjaslp.ailoop.databinding.ActivityRegisterBinding
import com.anjaslp.ailoop.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var loginViewModel: LoginViewModel

    override fun onStart() {
        super.onStart()
        loginViewModel.checkUserLoggedIn()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("Silahkan tunggu...")

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.loginResult.observe(this, { loggedIn ->
            if (loggedIn) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        })

        loginViewModel.errorMessage.observe(this, { error ->
            progressDialog.dismiss()
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })

        binding.btLogin.setOnClickListener {
            if (binding.editEmail.text.isNotEmpty() && binding.editPassword.text.isNotEmpty()) {
                val email = binding.editEmail.text.toString()
                val password = binding.editPassword.text.toString()
                loginViewModel.login(email, password)
                progressDialog.show()
            } else {
                Toast.makeText(this, "Silahkan isi Email dan Password terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
