package com.cargill.saltimpurity.extensions

import android.util.Patterns

fun String.isValidEmail(): Boolean {
    return if (this.contains('@')) {
        Patterns.EMAIL_ADDRESS.matcher(this).matches()
    } else {
        this.isNotBlank()
    }
}