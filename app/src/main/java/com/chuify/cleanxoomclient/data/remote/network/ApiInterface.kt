package com.chuify.cleanxoomclient.data.remote.network

import com.chuify.cleanxoomclient.data.remote.dto.*
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
    suspend fun listAccessories(): Response<AccessoryListDto>

    //----------------------------------------------------------------------------------------------------
    @Headers("authorized:true")
    @GET("vendors/{vendor_id}/products")
    suspend fun listProducts(@Path("vendor_id") vendorId: String): Response<ProductListDto>

    //----------------------------------------------------------------------------------------------------

    @Headers("authorized:true", "userid:true")
    @GET("address/user_id")
    suspend fun listLocations(): Response<LocationListDto>

    //----------------------------------------------------------------------------------------------------

    @Headers("authorized:true", "userid:true")
    @GET("orders/user_id/pendingorders")
    suspend fun listPendingOrders(): Response<OrderListDto>

    //----------------------------------------------------------------------------------------------------
    @Headers("authorized:true", "userid:true")
    @GET("orders/user_id/completedorders")
    suspend fun listCompletedOrders(): Response<OrderListDto>

    //----------------------------------------------------------------------------------------------------
    @Headers("authorized:true", "userid:true")
    @POST("orders/user_id")
    suspend fun saveOrder(@Body body: String): Response<SubmitOrderDto>

    //----------------------------------------------------------------------------------------------------
    @Headers("authorized:true")
    @GET("orders/{order_id}/rider")
    suspend fun trackOrder(@Path("order_id") order_id: String): Response<TrackDto>

    @Headers("authorized:true")
    @GET("payment/{order_id}")
    suspend fun confirmOrder(@Path("order_id") id: String): Response<PaymentDto>

    //----------------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------------
    @FormUrlEncoded
    @Headers("authorized:true", "userid:true")
    @POST("orders/user_id/cancel")
    suspend fun cancelOrder(
        @Field("order_id") order_id: String,
        @Field("reason") reason: String,
    ): Response<StatusDto>

    //----------------------------------------------------------------------------------------------------
    @Headers("authorized:true", "userid:true")
    @GET("notifications/user_id")
    suspend fun listNotifications(): Response<NotificationListDto>

    //----------------------------------------------------------------------------------------------------

    @Headers("authorized:true", "userid:true")
    @GET("loyalpoints/user_id")
    suspend fun getLoyalPoints(): Response<LoyaltyPointDto>

    //----------------------------------------------------------------------------------------------------
    @Headers("authorized:true", "userid:true")
    @FormUrlEncoded
    @POST("address/user_id")
    suspend fun saveAddress(
        @Field("title") address_url: String?,
        @Field("details") details: String?,
        @Field("instructions") instructions: String?,
        @Field("latitude") latitude: Double,
        @Field("longitude") longitude: Double,
    ): Response<StatusDto>


    //----------------------------------------------------------------------------------------------------
    @Headers("authorized:true", "userid:true")
    @FormUrlEncoded
    @PUT("users/user_id")
    suspend fun updateUser(
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("email") email: String
    ): Response<UserDto>

    //----------------------------------------------------------------------------------------------------
    @Headers("authorized:true", "userid:true")
    @FormUrlEncoded
    @POST("user/user_id/token")
    suspend fun updateFireBaseToken(@Field("token") token: String)


}