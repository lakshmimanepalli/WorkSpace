package com.cargill.saltimpurity.httpclient

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


interface ApiClient {

    @POST("{apiEndpoint}")
    suspend fun <C, T> request(@Body body: T, @Path("apiEndpoint") urlEndPoint: String?): Response<C>?

    @GET("{apiEndpoint}")
    fun <C> request(@Path("apiEndpoint") urlEndPoint: String?): Response<C>?

    @Multipart
    @POST("{apiEndpoint}")
    suspend fun <C> request(@Part image: MultipartBody.Part, @Path("apiEndpoint") urlEndPoint: String?): Response<C>?
}