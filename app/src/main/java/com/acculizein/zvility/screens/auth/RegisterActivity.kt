package com.acculizein.zvility.screens.auth

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acculizein.zvility.R
import com.acculizein.zvility.databinding.ActivityRegisterBinding
import com.acculizein.zvility.models.apis_models.request.RegisterRequest
import com.acculizein.zvility.models.apis_models.response.RegisterResponse
import com.acculizein.zvility.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        applyGradientToTextView()


        // Go To Login Screen
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            // Collecting Data from User Input text fields
            val username = binding.etRegisterUsername.text.toString().trim()
            val email = binding.etRegisterUserEmail.text.toString().trim()

            if (username.isEmpty() || email.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Please enter all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            registerUser(username, email)
        }

    }


    private fun registerUser(username: String, email: String) {
        val request = RegisterRequest(username, email)

        RetrofitClient.instance.registerUser(request)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody?.success == true) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Check your email for password",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Error: ${responseBody?.message.toString() ?: "Unknown error"}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        // Log the error response from the backend
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registration Failed: $errorBody",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Network Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }


    // Apply Gradient to TextView
    private fun applyGradientToTextView() {
        val paint = binding.tvTagLine.paint
        val width = paint.measureText(binding.tvTagLine.text.toString())

        val textShader = LinearGradient(
            0f, 0f, width, binding.tvTagLine.textSize,
            intArrayOf(
                0xFFDA4453.toInt(), // #DA4453 (Start Color)
                0xFF89216B.toInt()  // #89216B (End Color)
            ),
            null,
            Shader.TileMode.CLAMP
        )

        binding.tvTagLine.paint.shader = textShader
    }
}