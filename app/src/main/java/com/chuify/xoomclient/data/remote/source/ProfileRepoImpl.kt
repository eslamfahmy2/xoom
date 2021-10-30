package com.chuify.xoomclient.data.remote.source

import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.domain.model.User
import com.chuify.xoomclient.domain.repository.ProfileRepo
import com.chuify.xoomclient.domain.utils.ResponseState

class ProfileRepoImpl constructor(
    val sharedPrefs: SharedPrefs,
    val apiInterface: ApiInterface,
) : ProfileRepo {
    override suspend fun getUser(): User {
        return sharedPrefs.getUser()
    }

    override suspend fun getLoyaltyPoints() = try {
        val response = apiInterface.getLoyalPoints()
        if (response.isSuccessful) {
            response.body()?.let {
                ResponseState.Success(it)
            } ?: ResponseState.Error("api : boy is empty")
        } else {
            ResponseState.Error(response.errorBody()?.string())
        }
    } catch (e: Exception) {
        ResponseState.Error(e.message)
    }
}