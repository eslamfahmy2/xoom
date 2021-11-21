package com.chuify.cleanxoomclient.data.remote.network

import android.util.Log
import com.chuify.cleanxoomclient.data.prefrences.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response


class RequestUserIdInterceptor constructor(private val pref: SharedPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authorized = request.headers["userid"]
        return if (!authorized.isNullOrEmpty()) {
            val userId = pref.getUserID()
            Log.d("INTERCEPT", "intercept: $userId")
            val newUrl = chain.request().url.toString().replace("user_id", userId)
            Log.d("INTERCEPT", "newUrl: $newUrl")
            val newRequest = chain.request().newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        } else {
            val newRequest = chain.request().newBuilder().build()
            chain.proceed(newRequest)
        }
    }
}
