package com.cargill.saltimpurity

import android.app.Application
import com.cargill.saltimpurity.di.component.AppComponent
import com.cargill.saltimpurity.di.component.DaggerAppComponent

class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
            private set
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDagger()
    }

    private fun initDagger() {
        appComponent =
            DaggerAppComponent
                .builder()
                .application(this)
                .build()

        appComponent.inject(this)
    }
}