package com.chuify.xoomclient.data.remote.data_source

import android.app.Activity
import com.chuify.xoomclient.data.remote.dto.UserDto
import com.chuify.xoomclient.data.remote.network.ApiInterface
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.utils.ResponseState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepoImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val auth: FirebaseAuth,
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


    override suspend fun performPhoneAuth(phoneNumber: String ,  activity: Activity): PhoneAuthResult =
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
    ): ResponseState<UserDto> {

        val response = apiInterface.register(
            firstname = String(),
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

