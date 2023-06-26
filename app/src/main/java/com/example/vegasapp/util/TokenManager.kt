package com.example.vegasapp.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

object TokenManager {
    private const val PREFERENCES_NAME = "TokenPrefs"
    private const val TOKEN_KEY = "token"

    fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
        return sharedPreferences.getString(TOKEN_KEY, null)
    }
}