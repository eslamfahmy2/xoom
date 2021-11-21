package com.chuify.cleanxoomclient.domain.usecase.profile

import com.chuify.cleanxoomclient.data.prefrences.SharedPrefs
import com.chuify.cleanxoomclient.domain.mapper.UserDtoMapper
import com.chuify.cleanxoomclient.domain.model.User
import com.chuify.cleanxoomclient.domain.repository.ProfileRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import com.chuify.cleanxoomclient.domain.utils.Validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class UpdateUserUseCase @Inject constructor(
    private val profileRepo: ProfileRepo,
    private val sharedPrefs: SharedPrefs,
    private val userDtoMapper: UserDtoMapper
) {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String
    ) = flow<DataState<User>> {
        try {
            emit(DataState.Loading())
            if (!Validator.isValidName(firstName)) {
           //     throw Exception("first name not valid")
            }
            if (!Validator.isValidName(lastName)) {
                //         throw Exception("last name not valid")
            }
            if (!Validator.isValidEmail(email)) {
                //           throw Exception("email not valid")
            }
            when (val response = profileRepo.updateUser(
                firstName = firstName,
                lastName = lastName,
                email = email
            )) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {
                    val currentUser = sharedPrefs.getUser()
                    val updatedUser = response.data
                    val newUser = currentUser?.copy(
                        firstname = updatedUser.firstname,
                        lastname = updatedUser.lastname, email = updatedUser.email
                    )
                    newUser?.let {
                        sharedPrefs.saveUser(newUser)
                        val out = userDtoMapper.mapToDomainModel(newUser)
                        emit(DataState.Success(out))
                    } ?: kotlin.run {
                        emit(DataState.Error("Can't update profile at the moment"))
                    }

                }
            }


        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}