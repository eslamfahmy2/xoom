package com.chuify.cleanxoomclient.domain.usecase.auth

import android.app.Activity
import com.chuify.cleanxoomclient.data.remote.source.PhoneAuthResult
import com.chuify.cleanxoomclient.domain.repository.AuthRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.chuify.cleanxoomclient.domain.utils.Validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
    }.flowOn(Dispatchers.IO).conflate()
}