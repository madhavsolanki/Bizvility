package com.acculizein.zvility.network

import com.acculizein.zvility.models.apis_models.request.ForgotPasswordRequest
import com.acculizein.zvility.models.apis_models.request.LoginRequest
import com.acculizein.zvility.models.apis_models.response.LoginResponse
import com.acculizein.zvility.models.apis_models.request.RegisterRequest
import com.acculizein.zvility.models.apis_models.response.ForgotPasswordResponse
import com.acculizein.zvility.models.apis_models.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    fun registerUser(@Body request: RegisterRequest) : Call<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/forgot-password")
    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<ForgotPasswordResponse>

}