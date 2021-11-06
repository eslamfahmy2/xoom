package com.chuify.xoomclient.data.remote.dto

import com.chuify.xoomclient.data.prefrences.flow.NullableSerializer
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDto(


    @Expose
    @SerializedName(value = "Status", alternate = ["status"])
    val status: String?,

    @Expose
    @SerializedName(value = "Msg", alternate = ["msg", "message", "Message"])
    val msg: String?,

    @Expose
    @SerializedName(value = "User_id", alternate = ["user_id"])
    val user_id: String?,

    @Expose
    @SerializedName(value = "Firstname", alternate = ["firstname"])
    val firstname: String?,

    @Expose
    @SerializedName(value = "Lastname", alternate = ["lastname"])
    val lastname: String?,

    @Expose
    @SerializedName(value = "Email", alternate = ["email"])
    val email: String?,

    @Expose
    @SerializedName(value = "Phone", alternate = ["phone"])
    val phone: String?,

    @Expose
    @SerializedName(value = "Access_token", alternate = ["access_token"])
    val access_token: String?,
) : Serializable
