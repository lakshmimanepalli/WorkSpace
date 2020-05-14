package com.cargill.msalauth

import com.microsoft.identity.client.IAuthenticationResult

interface  MsalAuthenticationCallback{
    fun setToken(authresult: IAuthenticationResult,isApiToken: Boolean=false)
    fun setError(exception:Exception?)
}