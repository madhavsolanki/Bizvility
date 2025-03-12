package com.acculizein.zvility.screens.careers

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acculizein.zvility.R
import com.acculizein.zvility.databinding.ActivityCareersScreenBinding

class CareersScreen : AppCompatActivity() {
    private lateinit var binding: ActivityCareersScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCareersScreenBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Apply Gradient To Job Heading
        applyGradientToJobHeading()
    }

    private fun applyGradientToJobHeading() {
        val paint = binding.tvJobHeading.paint
        val width = paint.measureText(binding.tvJobHeading.text.toString())

        val textShader = LinearGradient(
            0f, 0f, width, binding.tvJobHeading.textSize,
            intArrayOf(
                0xFFDA4453.toInt(), // #DA4453 (Start Color)
                0xFF89216B.toInt()  // #89216B (End Color)
            ),
            null,
            Shader.TileMode.CLAMP
        )

        binding.tvJobHeading.paint.shader = textShader
    }
}