package com.chuify.cleanxoomclient.data.remote.source

import android.app.Activity
import com.chuify.cleanxoomclient.data.remote.dto.UserDto
import com.chuify.cleanxoomclient.data.remote.network.ApiInterface
import com.chuify.cleanxoomclient.domain.repository.AuthRepo
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepoImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val auth: FirebaseAuth,
) : AuthRepo {

    override suspend fun login(phone: String) = try {
        val response = apiInterface.login(phone = phone)
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

    override suspend fun updateFirebaseToken(token: String) {
       return apiInterface.updateFireBaseToken(token = token)
    }


    override suspend fun performPhoneAuth(
        phoneNumber: String,
        activity: Activity,
    ): PhoneAuthResult =
        suspendCoroutine { cont ->
            val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    cont.resume(
                        PhoneAuthResult.VerificationCompleted(credential)
                    )
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    cont.resumeWithException(e)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken,
                ) {
                    cont.resume(
                        PhoneAuthResult.CodeSent(verificationId, token)
                    )
                }
            }

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callback)
                .setActivity(activity)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }

    override suspend fun register(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
    ): ResponseState<UserDto> = try {
        val response = apiInterface.register(
            firstname = firstname,
            lastname = lastname,
            email = email,
            phone = phone
        )
        if (response.isSuccessful) {
            response.body()?.let {
                ResponseState.Success(it)
            } ?: ResponseState.Error("api : body is empty")
        } else {
            val jObjError = JSONObject(response.errorBody()!!.string())
            val message = jObjError.getString("error")
            ResponseState.Error(message)
        }

    } catch (e: Exception) {
        ResponseState.Error(e.message)
    }


}

