package com.chuify.xoomclient.domain.repository

import android.app.Activity
import com.chuify.xoomclient.data.remote.data_source.PhoneAuthResult
import com.chuify.xoomclient.data.remote.dto.UserDto
import com.chuify.xoomclient.domain.utils.ResponseState

interface AuthRepo {

    suspend fun login(phone: String): ResponseState<UserDto>

    suspend fun register(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
    ): ResponseState<UserDto>

    suspend fun performPhoneAuth(phoneNumber: String, activity: Activity): PhoneAuthResult
}