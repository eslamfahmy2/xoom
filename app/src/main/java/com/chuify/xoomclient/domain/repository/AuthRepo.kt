package com.chuify.xoomclient.domain.repository

import com.chuify.xoomclient.data.remote.dto.UserDto

interface AuthRepo {

    suspend fun login(phone: String): UserDto

    suspend fun register(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
    ): UserDto

}