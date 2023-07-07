package com.example.vegasapp.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import org.json.JSONException
import org.json.JSONObject
import android.util.Base64

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

    fun getSub(context: Context): String? {
        val token = getToken(context)
        return if (token != null) {
            val jwtParts = token.split(".")
            if (jwtParts.size == 3) {
                val encodedPayload = jwtParts[1]
                val decodedPayload = Base64.decode(encodedPayload, Base64.DEFAULT)
                try {
                    val jsonPayload = String(decodedPayload, Charsets.UTF_8)
                    val payloadObject = JSONObject(jsonPayload)
                    payloadObject.optString("sub")
                } catch (e: JSONException) {
                    null
                }
            } else {
                null
            }
        } else {
            null
        }
    }
}