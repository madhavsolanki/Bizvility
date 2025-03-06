package com.acculizein.zvility.screens.profile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.acculizein.zvility.R
import com.acculizein.zvility.adapters.SavedListingAdapter
import com.acculizein.zvility.databinding.ActivitySavedListingScreenBinding
import com.acculizein.zvility.models.SavedListing

class SavedListingScreen : AppCompatActivity() {
    private lateinit var binding : ActivitySavedListingScreenBinding

    private lateinit var adapter: SavedListingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedListingScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val savedPersons = listOf(
            SavedListing("Dr. Reecha Agarwal", "Health & Medical", R.drawable.doctor_one),
            SavedListing("Dr. John Doe", "Dental Care", R.drawable.doctor_two),
            SavedListing("Dr. Jane Smith", "Mental Health", R.drawable.doctor_three)
        )

        adapter = SavedListingAdapter(savedPersons)

        binding.rvSavedListings.apply {
            layoutManager = LinearLayoutManager(this@SavedListingScreen)
            this.adapter = this@SavedListingScreen.adapter
        }
    }
}