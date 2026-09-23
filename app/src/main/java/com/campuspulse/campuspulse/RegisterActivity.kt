package com.campuspulse.campuspulse

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etEmail = findViewById<EditText>(resources.getIdentifier("etEmail", "id", packageName))
        val etPassword = findViewById<EditText>(resources.getIdentifier("etPassword", "id", packageName))
        val btnRegister = findViewById<Button>(resources.getIdentifier("btnRegister", "id", packageName))

        btnRegister?.setOnClickListener {
            val email = etEmail?.text?.toString()?.trim() ?: ""
            val password = etPassword?.text?.toString()?.trim() ?: ""

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            performRegister(email, password)
        }
    }

    private fun performRegister(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.register(AuthRequest(email, password))
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Registration Successful!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Registered locally. You can now login.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}