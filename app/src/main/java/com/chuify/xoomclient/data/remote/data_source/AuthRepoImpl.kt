package com.chuify.xoomclient.data.remote.data_source

import com.chuify.xoomclient.data.remote.dto.UserDto
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.domain.repository.AuthRepo
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : AuthRepo {

    override suspend fun login(phone: String): UserDto {
        return apiInterface.login(phone = phone)
    }

    override suspend fun register(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
    ): UserDto {
        return apiInterface.register(
            firstname = firstname,
            lastname = lastname,
            email = email,
            phone = phone
        )
    }

}