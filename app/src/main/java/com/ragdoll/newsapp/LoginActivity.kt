package com.ragdoll.newsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ragdoll.newsapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.createNewAccTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        binding.loginBtn.setOnClickListener {
            /* Handle login logic here
               For example, you can validate the input fields and make a network request to login the user
               After successful login, you can navigate to the next activity or show a success message */
            val email = binding.emailEt.text.toString()
            val password = binding.passEt.text.toString()
            if (email.isBlank() || password.isBlank())
            // Show error message for empty fields
                Toast.makeText(this, "Empty field/s!", Toast.LENGTH_SHORT).show()
            else {
                // Proceed with login logic
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

        binding.forgotPassTv.setOnClickListener {
            /* Handle forgot password logic here
            For example, you can show a dialog or navigate to a reset password screen */
            Toast.makeText(this, "Forgot password clicked!", Toast.LENGTH_SHORT).show()
        }

    }
}