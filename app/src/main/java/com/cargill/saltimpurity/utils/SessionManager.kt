package com.cargill.saltimpurity.utils

import com.cargill.saltimpurity.repository.UserDataRepositoryImpl
import com.cargill.sharedprefs.SharedPreferencesStorage
import javax.inject.Inject
import javax.inject.Singleton

private const val REGISTERED_USER = "REGISTERED_USER"

@Singleton
class SessionManager @Inject constructor(private val shareprefs: SharedPreferencesStorage) {

    var userdatarepository: UserDataRepositoryImpl? = null

    val username: String
        get() = shareprefs.getString(REGISTERED_USER)

    fun isUserLoggedIn() = userdatarepository != null

    fun isUserRegistered() = shareprefs.getString(REGISTERED_USER).isNotEmpty()

    fun registerUser(username: String) {
        shareprefs.setString(REGISTERED_USER, username)
    }

    fun loginUser(username: String): Boolean {
        val registeredUser = this.username
        if (registeredUser != username) return false
        return true
    }


    fun unregister() {
        val username = shareprefs.getString(REGISTERED_USER)
        shareprefs.setString(REGISTERED_USER, "")

    }
}