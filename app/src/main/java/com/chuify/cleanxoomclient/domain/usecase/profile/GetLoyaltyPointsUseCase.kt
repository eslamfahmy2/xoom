package com.chuify.cleanxoomclient.domain.usecase.profile

import com.chuify.cleanxoomclient.data.prefrences.SharedPrefs
import com.chuify.cleanxoomclient.domain.repository.ProfileRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetLoyaltyPointsUseCase @Inject constructor(
    private val profileRepo: ProfileRepo,
    private val sharedPrefs: SharedPrefs
) {

    suspend operator fun invoke() = flow<DataState<String>> {
        try {
            emit(DataState.Loading())
            emit(DataState.Success(sharedPrefs.getPoints()))
            when (val response = profileRepo.getLoyaltyPoints()) {
                is ResponseState.Error -> {
                    emit(DataState.Error(response.message))
                }
                is ResponseState.Success -> {
                    response.data.loyalPoints?.let {
                        sharedPrefs.saveUserPoints(it)
                        emit(DataState.Success(it))
                    }
                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}