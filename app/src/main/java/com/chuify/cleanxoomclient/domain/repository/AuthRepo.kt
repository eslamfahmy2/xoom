package com.chuify.cleanxoomclient.domain.repository

import android.app.Activity
import com.chuify.cleanxoomclient.data.remote.dto.UserDto
import com.chuify.cleanxoomclient.data.remote.source.PhoneAuthResult
import com.chuify.cleanxoomclient.domain.utils.ResponseState

interface AuthRepo {

    suspend fun login(phone: String): ResponseState<UserDto>

    suspend fun updateFirebaseToken(token: String)

    suspend fun register(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
    ): ResponseState<UserDto>

    suspend fun performPhoneAuth(phoneNumber: String, activity: Activity): PhoneAuthResult
}