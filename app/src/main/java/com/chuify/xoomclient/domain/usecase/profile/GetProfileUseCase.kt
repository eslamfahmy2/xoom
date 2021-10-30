package com.chuify.xoomclient.domain.usecase.profile

import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.domain.model.User
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetProfileUseCase @Inject constructor(
    private val sharedPrefs: SharedPrefs,
) {
    suspend operator fun invoke() = flow<DataState<User>> {
        try {
            emit(DataState.Loading())
            val user = sharedPrefs.getUser()
            emit(DataState.Success(user))

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}