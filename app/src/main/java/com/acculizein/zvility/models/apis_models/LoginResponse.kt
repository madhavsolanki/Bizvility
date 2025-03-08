package com.acculizein.zvility.models.apis_models

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String?
)
