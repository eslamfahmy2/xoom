package com.chuify.xoomclient.domain.usecase.auth


import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.domain.mapper.UserDtoMapper
import com.chuify.xoomclient.domain.model.User
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import com.chuify.xoomclient.domain.utils.Validator
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repo: AuthRepo,
    private val mapper: UserDtoMapper,
    private val sharedPreferences: SharedPrefs,
) {

    suspend operator fun invoke(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
    ) = flow<DataState<User>> {
        try {
            emit(DataState.Loading())
            if (!Validator.isValidName(firstname)) {
                throw Exception("first name not valid")
            }
            if (!Validator.isValidName(lastname)) {
                throw Exception("last name not valid")
            }
            if (!Validator.isValidEmail(email)) {
                throw Exception("email not valid")
            }


            val response = repo.register(
                firstname = firstname,
                lastname = lastname,
                email = email,
                phone = phone
            )

            when (response) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {

                    if (response.data.status.equals("0")) {
                        throw Exception(response.data.msg)
                    }
                    sharedPreferences.saveUser(response.data)
                    val user = mapper.mapToDomainModel(response.data)
                    emit(DataState.Success(user))

                }
            }


        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }
}