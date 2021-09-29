package com.chuify.xoomclient.data.remote.network

import com.chuify.xoomclient.data.remote.dto.UserDto
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiInterface {

    @POST("login")
    suspend fun login(@Query("phone") phone: String): UserDto

    //----------------------------------------------------------------------------------------------------
    @POST("register")
    suspend fun register(
        @Query("firstname") firstname: String,
        @Query("lastname") lastname: String,
        @Query("email") email: String,
        @Query("phone") phone: String,
    ): UserDto


}