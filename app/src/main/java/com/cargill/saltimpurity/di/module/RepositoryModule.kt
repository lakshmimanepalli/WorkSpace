package com.cargill.saltimpurity.di.module

import com.cargill.saltimpurity.repository.IUserDataRepository
import com.cargill.saltimpurity.repository.UserDataRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object RepositoryModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideUserAccountDao(userdatarepo: UserDataRepositoryImpl): IUserDataRepository {
        return userdatarepo
    }
}