package com.chuify.xoomclient.data.remote.data_source

import com.chuify.xoomclient.data.remote.dto.UserDto
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.utils.ResponseState
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : AuthRepo {

    override suspend fun login(phone: String): ResponseState<UserDto> {

        val response = apiInterface.login(phone = phone)

        if (response.isSuccessful) {
            response.body()?.let {
                return ResponseState.Success(it)
            } ?: return ResponseState.Error()
        } else {
            return ResponseState.Error(response.errorBody().toString())
        }
    }

    override suspend fun register(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
    ): ResponseState<UserDto> {

        val response = apiInterface.register(
            firstname = firstname,
            lastname = lastname,
            email = email,
            phone = phone
        )
        if (response.isSuccessful) {
            response.body()?.let {
                return ResponseState.Success(it)
            } ?: return ResponseState.Error()
        } else {
            return ResponseState.Error(response.errorBody().toString())
        }
    }

}