package com.acculizein.zvility.screens.auth

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acculizein.zvility.MainActivity
import com.acculizein.zvility.R
import com.acculizein.zvility.databinding.ActivitySplashBinding
import com.acculizein.zvility.utils.SharedPrefManager
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Initialize SharedPrefManager
        sharedPrefManager = SharedPrefManager(this)

        // Apply Edge Insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Run Splash Animation
        runSplashAnimation()

        // Delay for 3 seconds then navigate based on token status
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000) // 3 seconds delay

            val token = sharedPrefManager.getToken()

            if (!token.isNullOrEmpty()) {
                // Token exists, navigate to MainActivity
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            } else {
                // No valid token, navigate to LoginActivity
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            finish() // Close SplashActivity
        }
    }

    private fun runSplashAnimation() {
        val fadeScaleAnim = AnimationUtils.loadAnimation(this, R.anim.fade_scale_up)
        binding.imageView.startAnimation(fadeScaleAnim)
        binding.textView.startAnimation(fadeScaleAnim)
    }
}
