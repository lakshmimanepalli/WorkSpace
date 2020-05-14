package com.cargill.msalauth

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.AnyThread
import com.cargill.msalauth.MsalAppConstants.Companion.AUTHORITY
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalClientException
import com.microsoft.identity.client.exception.MsalException
import com.microsoft.identity.client.exception.MsalServiceException
import com.microsoft.identity.client.exception.MsalUiRequiredException
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicReference

class MsalAuthencation(context: Context) {
    private val TAG = this.javaClass.simpleName
    private var mcontext: Context? = null
    private var mSingleAccountApp: ISingleAccountPublicClientApplication? = null
    private var mexception: Exception? = null
    private var msalAuthenticationCallback: MsalAuthenticationCallback? = null
    private var isAuthTokenCall = false
    private val mMsalAuthenctionInstance: AtomicReference<WeakReference<MsalAuthencation>> =
        AtomicReference(
            WeakReference<MsalAuthencation>(null)
        )

    init {
        mcontext = context
    }

    fun setOnClickListener(result: MsalAuthenticationCallback) {
        this.msalAuthenticationCallback = result
    }

    @AnyThread
    @Synchronized
    fun getAuthInstance(): MsalAuthencation {
        var msalAppAuth: MsalAuthencation? = mMsalAuthenctionInstance.get().get()
        if (msalAppAuth == null) {
            msalAppAuth = MsalAuthencation(mcontext!!)
            mMsalAuthenctionInstance.set(WeakReference(msalAppAuth))
        }
        return msalAppAuth
    }


    private fun getInstance(): IPublicClientApplication? {
        return try {
            try {
                setOnClickListener(mcontext as MsalAuthenticationCallback)
            } catch (e: Exception) {
            }
            if (mSingleAccountApp == null) {
                kotlin.run {
                    mSingleAccountApp =
                        PublicClientApplication.create(
                            mcontext!!,
                            R.raw.auth_config_single_account
                        ) as ISingleAccountPublicClientApplication
                }
            }
            mSingleAccountApp
        } catch (e: Exception) {
            msalAuthenticationCallback?.setError(e)
            null
        }
    }


    private fun getAuthTokeScopes(): Array<String> {
        val list = mutableListOf<String>()
        list.add(MsalAppConstants.MSAL_SCOPES)
        return list.toTypedArray()
    }

    private fun getScopes(): Array<String> {
        return ("user.read").toLowerCase().split(" ".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
    }

    private fun getAuthInteractiveCallback(): AuthenticationCallback {
        return object : AuthenticationCallback {

            override fun onSuccess(authenticationResult: IAuthenticationResult) {
                /* Successfully got a token, use it to call a protected resource - MSGraph */
                Log.d(TAG, "Successfully authenticated")
//                Toast.makeText(mcontext, "Successfully authenticated", Toast.LENGTH_SHORT)
//                    .show()
                msalAuthenticationCallback?.setToken(authenticationResult, isAuthTokenCall)
            }

            override fun onError(exception: MsalException) {
                mexception = exception
                /* Failed to acquireToken */
                Log.e(TAG, "Authentication failed: $exception")

                if (exception is MsalClientException) {
                    mexception = exception
                    Log.e(TAG, exception.stackTrace.toString())
                } else if (exception is MsalServiceException) {
                    mexception = exception
                    Log.e(TAG, exception.stackTrace.toString())
                }
                msalAuthenticationCallback?.setError(mexception)
            }

            override fun onCancel() {
                /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.")
                Toast.makeText(mcontext, "User cancelled login.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun getAuthSilentCallback(): AuthenticationCallback {
        return object : AuthenticationCallback {

            override fun onSuccess(authenticationResult: IAuthenticationResult) {
                Log.d(TAG, "Successfully authenticated")
//                Toast.makeText(mcontext, "Successfully authenticated", Toast.LENGTH_SHORT)
//                    .show()
                //Timber.d(TAG, "ID Token: " + authenticationResult.account.claims!!["id_token"])

                /* call graph */
                /* Successfully got a token, use it to call a protected resource - MSGraph */
                msalAuthenticationCallback?.setToken(authenticationResult, isAuthTokenCall)
            }

            override fun onError(exception: MsalException) {
                mexception = exception
                /* Failed to acquireToken */
                Log.e(TAG, "Authentication failed: $exception")
                if (exception is MsalClientException) {
                    mexception = exception
                    Log.e(TAG, exception.stackTrace.toString())
                    if (exception.errorCode == "invalid_parameter" || exception.errorCode == "no_tokens_found") {
                        initialiazeMSAL(true) //try logout and login again
                    }
                } else if (exception is MsalServiceException) {
                    mexception = exception
                    Log.e(TAG, exception.stackTrace.toString())
                } else if (exception is MsalUiRequiredException) {
                    mexception = exception
                    Log.e(TAG, exception.errorCode.toString())
                    refreshToken()
                }
                msalAuthenticationCallback?.setError(mexception)
            }

            override fun onCancel() {
                /* User canceled the authentication */
                Log.d(TAG, "User cancelled login.")
                Toast.makeText(mcontext, "User cancelled login.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun performOperationOnSignOut() {
        val signOutText = "Signing Out"
        Toast.makeText(mcontext, signOutText, Toast.LENGTH_LONG)
            .show()
        setupLogin()
    }

    fun refreshToken() {
        checkAuthInstance()
        isAuthTokenCall = true
        mSingleAccountApp!!.acquireToken(
            mcontext as Activity,
            getAuthTokeScopes(),
            getAuthInteractiveCallback()
        )
    }

    fun refreshTokenSilent() {
        checkAuthInstance()
        isAuthTokenCall = true
        mSingleAccountApp!!.acquireTokenSilentAsync(
            getAuthTokeScopes(),
            AUTHORITY,
            getAuthSilentCallback()
        )
    }

    fun initialiazeMSAL(isLogout: Boolean) {
        checkAuthInstance()
        try {
            when {
                mSingleAccountApp == null || mcontext !is Activity -> {
                    return
                }
                isLogout -> {
                    //Removes the signed-in account and cached tokens from this app.
                    mSingleAccountApp!!.signOut(object :
                        ISingleAccountPublicClientApplication.SignOutCallback {
                        override fun onSignOut() {
                            //SharedPrefs.setMsalLogout(false)
                            performOperationOnSignOut()
                        }

                        override fun onError(exception: MsalException) {
                            if (exception.errorCode == "no_current_account") {
                                initialiazeMSAL(false) // Login Again since there is not current account logged in
                            } else {
                                Log.e(TAG, exception.stackTrace.toString())
                            }
                        }
                    })
                }
                else -> {
                    setupLogin()
                }
            }

        } catch (e: IOException) {
            mexception = e
            Log.e(TAG, e.stackTrace.toString())
        } catch (e: Exception) {
            mexception = e
            Log.e(TAG, e.stackTrace.toString())
        } finally {
            if (mexception != null)
                msalAuthenticationCallback?.setError(mexception)
        }
    }

    //check if instace availble else get one
    private fun checkAuthInstance() {
        if (mSingleAccountApp == null) {
            getInstance()
        }
    }

    private fun setupLogin() {
        if (mSingleAccountApp == null || mcontext !is Activity) {
            return
        }
        println("setupLogin called")
        mSingleAccountApp!!.getCurrentAccountAsync(object :
            ISingleAccountPublicClientApplication.CurrentAccountCallback {
            override fun onAccountLoaded(activeAccount: IAccount?) {
                println("onAccountLoaded")
                if (activeAccount == null) {
                    isAuthTokenCall = false
                    mSingleAccountApp!!.signIn(
                        mcontext as Activity,
                        "",
                        getScopes(),
                        getAuthInteractiveCallback()
                    )
                    Log.d(TAG, "signIn In")
                } else {
                    Log.d(TAG, "CurrentAccount is Active")
                    Toast.makeText(mcontext, "CurrentAccount is Active", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onAccountChanged(priorAccount: IAccount?, currentAccount: IAccount?) {
                if (currentAccount == null) {
                    // Perform a cleanup task as the signed-in account changed.
                    performOperationOnSignOut()
                }
            }

            override fun onError(exception: MsalException) {
                mexception = exception
                msalAuthenticationCallback?.setError(mexception)
                println("Msal exception occured $exception")
                Log.e(TAG, exception.stackTrace.toString())
            }
        })
    }
}