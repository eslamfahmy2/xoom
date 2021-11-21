package com.chuify.cleanxoomclient.domain.usecase.profile

import com.chuify.cleanxoomclient.data.prefrences.SharedPrefs
import com.chuify.cleanxoomclient.domain.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ThemeUseCase @Inject constructor(
    private val sharedPrefs: SharedPrefs,
) {
    suspend fun getTheme() = flow<DataState<Boolean>> {
        try {
            val isDark = sharedPrefs.isDark()
            emit(DataState.Success(isDark))

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()

    suspend fun changeTheme(boolean: Boolean) = flow<DataState<Boolean>> {
        try {
            sharedPrefs.saveTheme(boolean)
            emit(DataState.Success(boolean))
        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}