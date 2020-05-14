package com.cargill.saltimpurity.ui.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.cargill.saltimpurity.R
import com.cargill.saltimpurity.base.BaseActivity
import com.cargill.saltimpurity.databinding.ActivityDisclaimerBinding
import com.cargill.saltimpurity.utils.AppConstants
import com.cargill.saltimpurity.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_disclaimer.view.*

class DisclaimerActivity : BaseActivity() {

    override val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisclaimerBinding.inflate(layoutInflater).apply {
            handlers = this@DisclaimerActivity
        }
        initializebinding()
        initView()
    }


    private fun initView() {
        val webView = binding.root.webview_disclaimer
        val webSettings = webView.settings
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.builtInZoomControls = false
        val res = resources
        val fontSize = res.getDimension(R.dimen.txtsize_html)
        webSettings.defaultFontSize = fontSize.toInt()
        webView.loadUrl(AppConstants.DISCLAIMER_ENG_PATH)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_accept_disclaimer -> {
                viewModel.acceptedTermsandConditions()
                startThisActivity(LoginActivity::class.java)
            }
            R.id.btn_cancel_disclaimer -> finish()
        }
    }

    private fun startThisActivity(calledActivity: Class<*>?) {
        val myIntent = Intent(this, calledActivity)
        this.startActivity(myIntent)
        finish()
    }
}
