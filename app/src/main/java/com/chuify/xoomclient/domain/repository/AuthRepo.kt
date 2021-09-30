package com.chuify.xoomclient.domain.repository

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

}