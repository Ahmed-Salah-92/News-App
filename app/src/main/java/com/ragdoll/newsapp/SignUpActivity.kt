package com.ragdoll.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ragdoll.newsapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.alreadyHaveAccTv.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.signUpBtn.setOnClickListener {
            /* Handle sign up logic here
               For example, you can validate the input fields and make a network request to sign up the user
               After successful sign up, you can navigate to the next activity or show a success message */
            val email = binding.emailEt.text.toString()
            val password = binding.passEt.text.toString()
            val confirmPassword = binding.confirmPassEt.text.toString()
            if (email.isBlank() || password.isBlank() || confirmPassword.isBlank())
            // Show error message for empty fields
                Toast.makeText(this, "Empty field/s!", Toast.LENGTH_SHORT).show()
            else if (password.length < 6)
            // Show error message for weak password
                Toast.makeText(this, "Password must be at least 6 characters!", Toast.LENGTH_SHORT)
                    .show()
            else if (password != confirmPassword)
            // Show error message for password mismatch
                Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show()
            else {
                // Proceed with sign up logic
                Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }
}