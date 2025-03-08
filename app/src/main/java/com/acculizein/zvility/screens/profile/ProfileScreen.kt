package com.acculizein.zvility.screens.profile

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acculizein.zvility.databinding.ActivityProfileScreenBinding

class ProfileScreen : AppCompatActivity() {
    private lateinit var binding: ActivityProfileScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnUpdateProfile.setOnClickListener {
            startActivity(Intent(this, UpdateProfileScreen::class.java))
        }

        binding.btnSavedListings.setOnClickListener {
            startActivity(Intent(this, SavedListingScreen::class.java))
        }

        binding.btnInbox.setOnClickListener {
            startActivity(Intent(this, InboxScreen::class.java))
        }

        binding.btnReviews.setOnClickListener {
            startActivity(Intent(this, ReviewsScreen::class.java))
        }

        applyGradientToExclusiveOfferText()
    }

    private fun applyGradientToExclusiveOfferText() {
        val paint = binding.tvProfile.paint
        val width = paint.measureText(binding.tvProfile.text.toString())

        val textShader = LinearGradient(
            0f, 0f, width, binding.tvProfile.textSize,
            intArrayOf(
                0xFFDA4453.toInt(), // #DA4453 (Start Color)
                0xFF89216B.toInt()  // #89216B (End Color)
            ),
            null,
            Shader.TileMode.CLAMP
        )

        binding.tvProfile.paint.shader = textShader
    }
}