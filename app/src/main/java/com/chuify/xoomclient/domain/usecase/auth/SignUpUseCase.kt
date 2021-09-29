package com.chuify.xoomclient.domain.usecase.auth

import com.chuify.xoomclient.domain.mapper.UserDtoMapper
import com.chuify.xoomclient.domain.model.User
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repo: AuthRepo,
    private val mapper: UserDtoMapper,
) {

    suspend operator fun invoke(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
    ) = flow<DataState<User>> {
        try {
            emit(DataState.Loading())
            val response = repo.register(
                firstname = firstname,
                lastname = lastname,
                email = email,
                phone = phone
            )
            val data = mapper.mapToDomainModel(response)
            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}