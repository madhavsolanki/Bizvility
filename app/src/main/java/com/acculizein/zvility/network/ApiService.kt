package com.acculizein.zvility.network

import com.acculizein.zvility.models.apis_models.LoginRequest
import com.acculizein.zvility.models.apis_models.LoginResponse
import com.acculizein.zvility.models.apis_models.RegisterRequest
import com.acculizein.zvility.models.apis_models.RegisterResponse
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
}