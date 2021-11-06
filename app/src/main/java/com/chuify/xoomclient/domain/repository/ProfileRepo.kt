package com.chuify.xoomclient.domain.repository

import com.chuify.xoomclient.data.remote.dto.LoyaltyPointDto
import com.chuify.xoomclient.data.remote.dto.UserDto
import com.chuify.xoomclient.domain.model.User
import com.chuify.xoomclient.domain.utils.ResponseState

interface ProfileRepo {

    suspend fun getUser(): UserDto?

    suspend fun getLoyaltyPoints(): ResponseState<LoyaltyPointDto>

    suspend fun updateUser(
        firstName: String,
        lastName: String,
        email: String
    ): ResponseState<UserDto>
}