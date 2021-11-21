package com.chuify.cleanxoomclient.domain.usecase.profile

import com.chuify.cleanxoomclient.data.prefrences.flow.FlowSharedPreferences
import com.chuify.cleanxoomclient.data.prefrences.flow.NullableSerializer
import com.chuify.cleanxoomclient.data.remote.dto.UserDto
import com.chuify.cleanxoomclient.domain.mapper.UserDtoMapper
import com.chuify.cleanxoomclient.domain.model.User
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ExperimentalCoroutinesApi
class GetProfileUseCase @Inject constructor(
    private val flowSharedPreferences: FlowSharedPreferences,
    private val userDtoMapper: UserDtoMapper
) {
    suspend operator fun invoke() = flow<DataState<User>> {
        try {

            emit(DataState.Loading())

            val serializer =
                object : NullableSerializer<UserDto> {
                    override fun deserialize(serialized: String?): UserDto? {
                        val gson = Gson()
                        return gson.fromJson(serialized, UserDto::class.java)
                    }

                    override fun serialize(value: UserDto?): String? {
                        val gson = Gson()
                        return gson.toJson(value)
                    }
                }

            flowSharedPreferences.getUser("USER", serializer, defaultValue = null).asFlow()
                .collect {
                    it?.let {
                        val user = userDtoMapper.mapToDomainModel(it)
                        emit(DataState.Success(user))
                    } ?: kotlin.run {
                        emit(DataState.Error("please login, no user found"))
                    }
                }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}