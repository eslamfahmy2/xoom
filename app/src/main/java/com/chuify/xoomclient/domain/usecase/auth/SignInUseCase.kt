package com.chuify.xoomclient.domain.usecase.auth

import android.util.Log
import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.domain.mapper.UserDtoMapper
import com.chuify.xoomclient.domain.model.User
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import com.chuify.xoomclient.domain.utils.Validator
import com.chuify.xoomclient.presentation.ui.signup.TAG
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repo: AuthRepo,
    private val mapper: UserDtoMapper,
    private val sharedPreferences: SharedPrefs,
) {

    suspend operator fun invoke(
        phone: String,
    ) = flow<DataState<User>> {
        try {
            emit(DataState.Loading())
            if (!Validator.isValidPhone(phone)) {
                //  throw Exception("phone not valid")
            }
            val response = repo.login(
                phone = phone
            )

            when (response) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {
                    if (response.data.status.equals("1")) {
                        val data = mapper.mapToDomainModel(response.data)
                        sharedPreferences.saveUser(response.data)
                        emit(DataState.Success(data))
                    } else {
                        Log.d(TAG, "invoke: isNullOrEmpty " + response.data)
                        emit(DataState.Error(response.data.msg))
                    }
                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}