package com.cargill.saltimpurity.base

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.cargill.saltimpurity.BR
import com.cargill.saltimpurity.MainApplication
import com.cargill.saltimpurity.di.factory.ViewModelFactory
import com.cargill.saltimpurity.utils.SessionManager
import com.cargill.msalauth.MsalAuthencation
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    //region properties
    protected lateinit var binding: ViewDataBinding

    protected lateinit var mMaslAppAuth: MsalAuthencation

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    protected abstract val viewModel: BaseViewModel?

    @set:Inject
    internal lateinit var sessionManager: SessionManager
    //endregion properties

    override fun onCreate(savedInstanceState: Bundle?) {
        MainApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        showFullScreenActivity() //show full screen
    }

    protected fun initializebinding() {
        if (::binding.isInitialized) {
            val view = binding.root
            setContentView(view)
            viewModel?.let {
                lifecycle.addObserver(it)
                binding.setVariable(BR.vm, viewModel)
                binding.lifecycleOwner = this
            }

        }
    }

    //region Msal Initialization
    private fun initiliazeAuthentication() {
        if (!::mMaslAppAuth.isInitialized)
            mMaslAppAuth = MsalAuthencation(this).getAuthInstance()
    }
    //endregion Msal Initialization

    //region UI
    private fun showFullScreenActivity() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager
                .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
    //endregion UI
}
