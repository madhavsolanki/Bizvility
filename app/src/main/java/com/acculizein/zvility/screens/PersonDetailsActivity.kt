package com.acculizein.zvility.screens

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acculizein.zvility.databinding.ActivityPersonDetailsBinding
import com.acculizein.zvility.models.Person

class PersonDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        // Retrieve data from intent
        val person = intent.getParcelableExtra<Person>("person")

        person?.let {
            binding.ivPerson.setImageResource(it.imageResId)
            binding.tvPersonName.text = it.name
            binding.tvCategory.text = it.category
            binding.tvLocation.text = it.location
        }
    }
}