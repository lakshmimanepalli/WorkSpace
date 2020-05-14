package com.cargill.saltimpurity.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cargill.saltimpurity.model.UserAccount
import com.cargill.dao.base.BaseDao

@Dao
interface UserAccountDao : BaseDao<UserAccount> {

    @Query("SELECT * FROM user_account WHERE email = :email")
    suspend fun searchByEmail(email: String): UserAccount?

    @Query("SELECT * FROM user_account WHERE id = :id")
    suspend fun searchByPk(id: Int): UserAccount

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(accountProperties: UserAccount): Long

    @Query("UPDATE user_account SET email = :email, username = :username WHERE id = :id")
    suspend fun updateAccountProperties(id: Int, email: String, username: String)

    @Delete
    suspend fun removeUser(user: UserAccount)

    @Query("SELECT * FROM user_account")
    fun getAllUserAccounts(): LiveData<List<UserAccount>>
}