package com.cargill.saltimpurity.ui.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.cargill.saltimpurity.base.BaseActivity
import com.cargill.saltimpurity.databinding.ActivityLoginBinding
import com.cargill.saltimpurity.extensions.toast
import com.cargill.saltimpurity.viewmodels.LoginViewModel


class LoginActivity : BaseActivity() {

    override val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        initializebinding()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.isSuccess.observe(this, Observer {
            if (it) {
                "Login Success".toast(this, 0)
            } else {
                "Login Failed".toast(this, 0)
            }
        })
    }
}
