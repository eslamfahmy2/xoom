package com.chuify.xoomclient.domain.repository

import com.chuify.xoomclient.data.remote.dto.LoyaltyPointDto
import com.chuify.xoomclient.domain.model.User
import com.chuify.xoomclient.domain.utils.ResponseState

interface ProfileRepo {

    suspend fun getUser(): User

    suspend fun getLoyaltyPoints(): ResponseState<LoyaltyPointDto>
}