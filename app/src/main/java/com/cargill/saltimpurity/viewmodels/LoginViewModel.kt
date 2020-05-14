package com.cargill.saltimpurity.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cargill.saltimpurity.base.BaseViewModel
import com.cargill.saltimpurity.common.SingleLiveEvent
import com.cargill.saltimpurity.model.UserAccount
import com.cargill.saltimpurity.repository.IUserDataRepository
import com.cargill.saltimpurity.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    val sessionManager: SessionManager,
    val userRepo: IUserDataRepository
) : BaseViewModel() {

    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var acceptedTCs = MutableLiveData<Boolean>()

    val isSuccess = SingleLiveEvent<Boolean>()

    init {
        acceptedTCs.value = false
    }

    fun login() {
        registerUser()
    }

    private fun updateUserData(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.AddOrUpdateUser(
                UserAccount(
                    0,
                    username,
                    password
                )
            )
        }
    }

    fun acceptedTermsandConditions() {
        acceptedTCs.value = true
    }

    fun registerUser() {
        if (username.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
            isSuccess.postValue(false)
        } else {
            isSuccess.postValue(true)
            sessionManager.registerUser(username.value!!)
            updateUserData(username.value.toString(), password.value.toString())
        }
    }
}