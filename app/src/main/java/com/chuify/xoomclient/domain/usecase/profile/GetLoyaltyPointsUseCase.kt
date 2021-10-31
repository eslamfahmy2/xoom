package com.chuify.xoomclient.domain.usecase.profile

import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.domain.repository.ProfileRepo
import com.chuify.xoomclient.domain.utils.DataState
import com.chuify.xoomclient.domain.utils.ResponseState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetLoyaltyPointsUseCase @Inject constructor(
    private val profileRepo: ProfileRepo,
    private val sharedPrefs: SharedPrefs
) {

    suspend operator fun invoke() = flow<DataState<String>> {
        try {
            emit(DataState.Loading())
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
    }
}