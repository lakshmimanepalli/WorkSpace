package com.cargill.saltimpurity.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cargill.saltimpurity.model.UserAccount
import com.cargill.saltimpurity.repository.dao.UserAccountDao

@Database(entities = [UserAccount::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAccountPropertiesDao(): UserAccountDao
}
