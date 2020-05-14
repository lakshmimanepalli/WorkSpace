package com.cargill.saltimpurity.di

import com.cargill.sharedprefs.ISharePrefsStorage
import com.cargill.sharedprefs.SharedPreferencesStorage
import dagger.Binds
import dagger.Module

@Module
abstract class SharedPrefsModule {
    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): ISharePrefsStorage
}