package com.cargill.saltimpurity.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cargill.saltimpurity.di.annoatation.ViewModelKey
import com.cargill.saltimpurity.di.factory.ViewModelFactory
import com.cargill.saltimpurity.viewmodels.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindMainViewModel(loginViewModel: LoginViewModel): ViewModel
}