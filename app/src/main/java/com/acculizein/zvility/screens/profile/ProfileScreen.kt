package com.acculizein.zvility.screens.profile

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acculizein.zvility.databinding.ActivityProfileScreenBinding
import com.acculizein.zvility.screens.auth.LoginActivity
import com.acculizein.zvility.utils.SharedPrefManager

class ProfileScreen : AppCompatActivity() {
    private lateinit var binding: ActivityProfileScreenBinding

    private lateinit var sharedPrefManager: SharedPrefManager

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
        // Initialize SharedPrefManager
        sharedPrefManager = SharedPrefManager(this)

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

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }

        applyGradientToExclusiveOfferText()
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("OK"){_, _ ->
                logoutUser()
            }
            .setNegativeButton("Cancel"){ dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logoutUser() {
        sharedPrefManager.clearToken()  // clear token from shared preferences

        Toast.makeText(this@ProfileScreen, "Logging out...", Toast.LENGTH_SHORT).show()

        // Delay of 2 seconds before redirecting to LoginActivity

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@ProfileScreen, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }, 2000)
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