package com.acculizein.zvility.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object{
        private const val PREF_NAME = "AppPrefs"
        private const val KEY_JWT_TOKEN = "jwt_token"
    }

    fun saveToken(token: String){
        sharedPreferences.edit().putString(KEY_JWT_TOKEN, token).apply()
    }

    fun getToken(): String ? {
        return sharedPreferences.getString(KEY_JWT_TOKEN, null)
    }

    fun clearToken(){
        sharedPreferences.edit().remove(KEY_JWT_TOKEN).apply()
    }

}