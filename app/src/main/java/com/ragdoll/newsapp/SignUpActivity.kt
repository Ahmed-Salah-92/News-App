package com.ragdoll.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ragdoll.newsapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

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

        // Initialize Firebase Auth
        auth = Firebase.auth

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
                binding.loadingProgressBar.isVisible = true
                addUserToFirebase(email, password)
            }
        }
    }

    private fun addUserToFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                    // Sign in success, update UI with the signed-in user's information
                    verifyEmail()
                 else {
                    // If sign in fails, display a message to the user.
                    binding.loadingProgressBar.isVisible = false
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun verifyEmail() {
        val user = Firebase.auth.currentUser

        user!!.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.loadingProgressBar.isVisible = false
                    Toast.makeText(this, "Check your email!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

