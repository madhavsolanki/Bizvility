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
import com.acculizein.zvility.MainActivity
import com.acculizein.zvility.R
import com.acculizein.zvility.databinding.ActivityLoginBinding
import com.acculizein.zvility.models.apis_models.request.ForgotPasswordRequest
import com.acculizein.zvility.models.apis_models.request.LoginRequest
import com.acculizein.zvility.models.apis_models.response.ForgotPasswordResponse
import com.acculizein.zvility.models.apis_models.response.LoginResponse
import com.acculizein.zvility.network.RetrofitClient
import com.acculizein.zvility.utils.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        applyGradientToTextView()

        // Initialize SharedPrefManager
        sharedPrefManager = SharedPrefManager(this)

        // Got to Signup screen
        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Please enter email and password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        binding.tvForgotPassword.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Please enter you email", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            forgotPassword(email)
        }

    }

    private fun forgotPassword(email: String) {
        val request = ForgotPasswordRequest(email)

        RetrofitClient.instance.forgotPassword(request)
            .enqueue(object : Callback<ForgotPasswordResponse> {
                override fun onResponse(
                    call: Call<ForgotPasswordResponse>,
                    response: Response<ForgotPasswordResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Check your email for new password",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            response.errorBody()?.string() ?: "Failed to reset password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    private fun loginUser(email: String, password: String) {
        val request = LoginRequest(email, password)

        RetrofitClient.instance.loginUser(request)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val token = response.body()?.token

                        if (!token.isNullOrEmpty()) {
                            // Save Token to SharedPrefManager
                            sharedPrefManager.saveToken(token)
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Navigate to MainActivity
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid Credentials",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error: ${t.message.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    private fun applyGradientToTextView() {
        val paint = binding.tvWelcome.paint
        val width = paint.measureText(binding.tvWelcome.text.toString())

        val textShader = LinearGradient(
            0f, 0f, width, binding.tvWelcome.textSize,
            intArrayOf(
                0xFFDA4453.toInt(), // #DA4453 (Start Color)
                0xFF89216B.toInt()  // #89216B (End Color)
            ),
            null,
            Shader.TileMode.CLAMP
        )

        binding.tvWelcome.paint.shader = textShader
    }
}
