package com.acculizein.zvility.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object{
        private const val PREF_NAME = "AppPrefs"
        private const val KEY_JWT_TOKEN = "jwt_token"
        private const val KEY_TOKEN_TIMESTAMP = "token_timestamp" // To track token storage time
        private const val EXPIRATION_TIME = 24 * 60 * 60 * 1000 // 24 hours in milliseconds
    }

    fun saveToken(token: String){
        val currentTime = System.currentTimeMillis()
        sharedPreferences.edit()
            .putString(KEY_JWT_TOKEN, token)
            .putLong(KEY_TOKEN_TIMESTAMP, currentTime) // Save the time of token storage
            .apply()
    }

    fun getToken(): String? {
        val token = sharedPreferences.getString(KEY_JWT_TOKEN, null)
        val storedTime = sharedPreferences.getLong(KEY_TOKEN_TIMESTAMP, 0)

        return if (token != null && isTokenExpired(storedTime)) {
            clearToken() // If expired, remove it
            null
        } else {
            token
        }
    }

    private fun isTokenExpired(storedTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - storedTime) > EXPIRATION_TIME // Token older than 24 hours
    }

    fun clearToken() {
        sharedPreferences.edit()
            .remove(KEY_JWT_TOKEN)
            .remove(KEY_TOKEN_TIMESTAMP)
            .apply()
    }
}