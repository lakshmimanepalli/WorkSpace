package com.cargill.saltimpurity.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cargill.saltimpurity.MainApplication
import com.cargill.saltimpurity.di.factory.ViewModelFactory
import com.cargill.saltimpurity.httpclient.ApiClient
import javax.inject.Inject

abstract class BaseFragment: Fragment() {

    @Inject
    lateinit var apiClient: ApiClient

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent.inject(this)
    }
}