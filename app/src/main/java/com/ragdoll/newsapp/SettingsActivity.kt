package com.ragdoll.newsapp

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ragdoll.newsapp.databinding.ActivitySettingsBinding
import androidx.core.content.edit

class SettingsActivity : AppCompatActivity() {
    // View Binding
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // set up the radio buttons
        binding.countriesRg.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.us_rb -> changeCountryCode("us")
                R.id.de_rb -> changeCountryCode("de")
            }
        }
    }

    private fun changeCountryCode(countryCode: String) {
        getSharedPreferences("settings", MODE_PRIVATE).edit {
            putString("countryCode", countryCode)
        }
        Toast.makeText(this, "Changed!", Toast.LENGTH_SHORT).show()
    }
}