package com.chuify.xoomclient.domain.usecase.auth

import android.app.Activity
import com.chuify.xoomclient.data.remote.source.PhoneAuthResult
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.Validator
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticatePhoneUseCase @Inject constructor(
    private val repo: AuthRepo,
) {

    suspend operator fun invoke(
        phone: String,
        activity: Activity,
    ) = flow<DataState<String>> {
        try {
            emit(DataState.Loading())
            if (!Validator.isValidPhone(phone)) {
                //   throw Exception("phone not valid")
            }
            val response = repo.performPhoneAuth(
                phoneNumber = phone,
                activity = activity
            )

            when (response) {
                is PhoneAuthResult.CodeSent -> {
                    emit(DataState.Success(response.verificationId))
                }
                is PhoneAuthResult.VerificationCompleted -> {
                    emit(DataState.Success(response.credentials.toString()))
                }
            }


        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}