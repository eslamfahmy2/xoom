package com.chuify.xoomclient.data.remote.network

import com.chuify.xoomclient.data.prefrences.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(private val pref: SharedPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        /*
        val token = pref.getToken()
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Token 9c8b06d329136da358c2d00e76946b0111ce2c48")
            .build()
        */
        val newRequest = chain.request().newBuilder().build()
        return chain.proceed(newRequest)
    }
}
