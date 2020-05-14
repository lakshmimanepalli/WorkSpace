package com.cargill.saltimpurity.di.component

import android.app.Application
import com.cargill.saltimpurity.MainApplication
import com.cargill.saltimpurity.base.BaseActivity
import com.cargill.saltimpurity.base.BaseFragment
import com.cargill.saltimpurity.di.SharedPrefsModule
import com.cargill.saltimpurity.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, ViewModelModule::class, DatabaseModule::class, RepositoryModule::class,
        AppModule::class, ApiModule::class, ViewModelFactoryModule::class, SharedPrefsModule::class]
)
interface AppComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(activity: BaseActivity)
    fun inject(baseFragment: BaseFragment)
}
