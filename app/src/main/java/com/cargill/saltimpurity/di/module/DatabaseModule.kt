package com.cargill.saltimpurity.di.module

import android.app.Application
import androidx.room.Room
import com.cargill.saltimpurity.database.AppDatabase
import com.cargill.saltimpurity.repository.dao.UserAccountDao
import com.cargill.saltimpurity.utils.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @JvmStatic
    @Singleton
    @Provides
    fun provideAppDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppConstants.DATABASE_NAME)
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideUserAccountDao(db: AppDatabase): UserAccountDao {
        return db.getAccountPropertiesDao()
    }

}