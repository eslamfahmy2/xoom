package com.chuify.cleanxoomclient.domain.repository

import com.chuify.cleanxoomclient.data.remote.dto.LoyaltyPointDto
import com.chuify.cleanxoomclient.data.remote.dto.UserDto
import com.chuify.cleanxoomclient.domain.utils.ResponseState

interface ProfileRepo {

    suspend fun getUser(): UserDto?

    suspend fun getLoyaltyPoints(): ResponseState<LoyaltyPointDto>

    suspend fun updateUser(
        firstName: String,
        lastName: String,
        email: String
    ): ResponseState<UserDto>
}