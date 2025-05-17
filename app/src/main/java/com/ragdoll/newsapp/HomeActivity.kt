package com.ragdoll.newsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ragdoll.newsapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up the buttons
        binding.techBtn.setOnClickListener {
            category = "technology"
            navigateToNewsActivity()
        }
        binding.sportsBtn.setOnClickListener {
            category = "sports"
            navigateToNewsActivity()
        }
        binding.scienceBtn.setOnClickListener {
            category = "science"
            navigateToNewsActivity()
        }

        // Set up the toolbar
        binding.toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings_item -> {
                    // Handle search action
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }

                else -> false
            }
        }


    }

    fun navigateToNewsActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}


