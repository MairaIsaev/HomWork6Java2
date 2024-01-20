package com.example.homwork6java2

import android.content.Context

class PreferenceHelper(context: Context) {

    private val preferenceHelper = context.getSharedPreferences("My pref", Context.MODE_PRIVATE)

    var isHasPemission: Boolean
        get() = preferenceHelper.getBoolean(IS_HAS_PERMISSION, false)
        set(value) {
            preferenceHelper.edit().putBoolean(IS_HAS_PERMISSION, value).apply()
        }

    companion object {
        private val IS_HAS_PERMISSION = "permission"
    }
}