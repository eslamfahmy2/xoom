package com.chuify.cleanxoomclient.data.remote.network

import com.chuify.cleanxoomclient.data.prefrences.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response


class RequestAuthenticationInterceptor constructor(private val pref: SharedPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authorized = request.headers["authorized"]
        return if (!authorized.isNullOrEmpty()) {
            val token = pref.getToken()
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        } else {
            val newRequest = chain.request().newBuilder().build()
            chain.proceed(newRequest)
        }
    }
}
