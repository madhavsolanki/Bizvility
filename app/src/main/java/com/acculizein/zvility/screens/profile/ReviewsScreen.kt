package com.acculizein.zvility.screens.profile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.acculizein.zvility.R
import com.acculizein.zvility.adapters.ReviewAdapter
import com.acculizein.zvility.databinding.ActivityReviewsScreenBinding
import com.acculizein.zvility.models.Review

class ReviewsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityReviewsScreenBinding
    private lateinit var adapter: ReviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewsScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val reviews = listOf(
            Review("John Doe", "Dr. Reecha Agarwal", 5, "March 24, 2025", "Great"),
            Review("Jane Smith", "Dr. John Doe", 4, "March 22, 2025", "Good Experience"),
            Review("Alice Johnson", "Dr. Jane Smith", 3, "March 20, 2025", "Okay")
        )

        adapter = ReviewAdapter(reviews, onEditClick = { review ->
            Toast.makeText(this, "Edit Review: ${review.reviewText}", Toast.LENGTH_SHORT).show()
        }, onDeleteClick = { review ->
            Toast.makeText(this, "Delete Review: ${review.reviewText}", Toast.LENGTH_SHORT).show()
        })

        binding.rvReviews.apply {
            layoutManager = LinearLayoutManager(this@ReviewsScreen)
            this.adapter = this@ReviewsScreen.adapter// Adds spacing between items
        }
    }
}