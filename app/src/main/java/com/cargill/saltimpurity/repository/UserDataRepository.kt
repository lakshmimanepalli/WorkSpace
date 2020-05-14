package com.cargill.saltimpurity.repository

import com.cargill.saltimpurity.model.UserAccount
import com.cargill.saltimpurity.repository.dao.UserAccountDao
import com.cargill.saltimpurity.utils.SessionManager
import javax.inject.Inject

interface IUserDataRepository {
    suspend fun AddOrUpdateUser(userAccount: UserAccount)
}

class UserDataRepositoryImpl @Inject constructor(
    private val userManager: SessionManager,
    private val userdao: UserAccountDao
) : IUserDataRepository {

    val username: String
        get() = userManager.username

    override suspend fun AddOrUpdateUser(userAccount: UserAccount) {
        userdao.insertorReplace(userAccount)
    }

}