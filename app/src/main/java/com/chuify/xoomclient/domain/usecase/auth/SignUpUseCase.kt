package com.chuify.xoomclient.domain.usecase.auth


import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.domain.repository.AuthRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import com.chuify.xoomclient.domain.utils.Validator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repo: AuthRepo,
    private val sharedPreferences: SharedPrefs,
) {

    suspend operator fun invoke(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
    ) = flow<DataState<LoginResult>> {
        try {
            //a01147449874
            emit(DataState.Loading())
            val phoneNumber = "+254$phone"
            if (!Validator.isValidPhone(phoneNumber)) {
                throw Exception("phone not valid")
            }
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
                phone = phoneNumber
            )

            when (response) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {

                    when {
                        response.data.status.equals("0") -> {
                            emit(DataState.Error(response.data.msg))
                        }
                        response.data.status.equals("1") -> {
                            sharedPreferences.saveUser(response.data)
                            sharedPreferences.saveToken(response.data.access_token)
                            sharedPreferences.saveUserID(response.data.user_id)
                            sharedPreferences.saveUser(response.data)
                            emit(DataState.Success(LoginResult.Login))
                        }
                        else -> {
                            emit(DataState.Error(response.data.msg))
                        }
                    }

                }
            }


        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}