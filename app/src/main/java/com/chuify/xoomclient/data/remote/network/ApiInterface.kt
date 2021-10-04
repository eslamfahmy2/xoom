package com.chuify.xoomclient.data.remote.network

import com.chuify.xoomclient.data.remote.dto.UserDto
import com.chuify.xoomclient.data.remote.dto.VendorListDto
import com.chuify.xoomclient.domain.model.Accessory
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {

    @POST("login")
    suspend fun login(@Query("phone") phone: String): Response<UserDto>

    //----------------------------------------------------------------------------------------------------
    @POST("register")
    suspend fun register(
        @Query("firstname") firstname: String,
        @Query("lastname") lastname: String,
        @Query("email") email: String,
        @Query("phone") phone: String,
    ): Response<UserDto>

    //----------------------------------------------------------------------------------------------------
    @Headers("authorized:true")
    @GET("vendors")
    suspend fun listVendors(): Response<VendorListDto>

    //----------------------------------------------------------------------------------------------------
    @Headers("authorized:true")
    @GET("accessories")
    suspend fun listAccessories(): Response<Accessory>

    //----------------------------------------------------------------------------------------------------
    @Headers("authorized:true")
    @GET("vendors/{vendor_id}/products")
    suspend fun listProducts(@Path("vendor_id") vendorId: String): Response<Accessory>

    //----------------------------------------------------------------------------------------------------


}