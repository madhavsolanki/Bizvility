package com.acculizein.zvility.models

data class Review(
    val loggedInUser: String,
    val verifiedUser: String,
    val rating: Int,
    val reviewDate: String,
    val reviewText: String
)

