package com.app.cheatbite

import android.content.SharedPreferences
import androidx.core.content.edit

class UserSharedPreference(
    private val sharedPreferences: SharedPreferences
) {

    fun saveUserStatus(loggedIn: Boolean) {
        sharedPreferences.edit(commit = true) { putBoolean(KEY_USER, loggedIn)}
    }

    fun getUserStatus(): Boolean {
        val savedId = sharedPreferences.getBoolean(KEY_USER, false)
        return savedId
    }

    companion object {
        private const val KEY_USER = "user_key"
    }
}