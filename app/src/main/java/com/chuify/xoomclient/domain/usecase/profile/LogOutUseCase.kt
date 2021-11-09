package com.chuify.xoomclient.domain.usecase.profile

import com.chuify.xoomclient.data.prefrences.SharedPrefs
import com.chuify.xoomclient.data.prefrences.flow.FlowSharedPreferences
import com.chuify.xoomclient.domain.repository.CartRepo
import com.chuify.xoomclient.domain.repository.NotificationRepo
import com.chuify.xoomclient.domain.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ExperimentalCoroutinesApi
class LogOutUseCase @Inject constructor(
    private val sharedPrefs: SharedPrefs,
    private val flowSharedPreferences: FlowSharedPreferences,
    private val notificationRepo: NotificationRepo,
    private val cartRepo: CartRepo
) {

    suspend operator fun invoke() = flow<DataState<Nothing>> {
        try {
            val theme = sharedPrefs.isDark()
            sharedPrefs.clear()
            flowSharedPreferences.clear()
            notificationRepo.clear()
            cartRepo.clear()
            sharedPrefs.saveTheme(theme)
            emit(DataState.Success())

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()
}