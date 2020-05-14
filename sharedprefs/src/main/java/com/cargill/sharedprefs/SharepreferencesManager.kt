package com.cargill.sharedprefs

import android.app.Application
import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

interface ISharePrefsStorage {
    fun setString(key: String, value: String)
    fun getString(key: String): String
}

@Singleton
class SharedPreferencesStorage @Inject constructor(application: Application) : ISharePrefsStorage {

    private val sharedPreferences =
        application.getSharedPreferences("yourappname", Context.MODE_PRIVATE)

    override fun setString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }
}