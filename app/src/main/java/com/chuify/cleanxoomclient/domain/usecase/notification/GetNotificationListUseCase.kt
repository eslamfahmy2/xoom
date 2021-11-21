package com.chuify.cleanxoomclient.domain.usecase.notification

import com.chuify.cleanxoomclient.data.remote.dto.NotificationDto
import com.chuify.cleanxoomclient.domain.mapper.NotificationEntityMapper
import com.chuify.cleanxoomclient.domain.model.Notification
import com.chuify.cleanxoomclient.domain.repository.NotificationRepo
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.chuify.cleanxoomclient.domain.utils.ResponseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class GetNotificationListUseCase @Inject constructor(
    private val notificationRepo: NotificationRepo,
    private val notificationEntityMapper: NotificationEntityMapper,
) {

    suspend operator fun invoke() = flow<DataState<List<Notification>>> {
        try {
            emit(DataState.Loading())
            when (val result = notificationRepo.listNotificationApi()) {
                is ResponseState.Error -> {
                    emit(DataState.Error(result.message))
                }
                is ResponseState.Success -> {

                    result.data.notifications?.forEach { it ->

                        val entity = notificationEntityMapper.dtoToEntity(
                            model = it,
                            time = getTime(it),
                            open = false)

                        notificationRepo.insert(entity)
                    }

                    notificationRepo.listNotifications().collect { it ->
                        val res = it.map { notificationEntityMapper.mapToDomainModel(it) }
                        emit(DataState.Success(res))
                    }
                }
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message))
        }
    }.flowOn(Dispatchers.IO).conflate()

    private fun getTime(notificationDto: NotificationDto): String = try {
        notificationDto.date?.let {
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = df.parse(notificationDto.date)
            val prettyTime = PrettyTime(Locale.US)
            prettyTime.format(date)
        } ?: ""
    } catch (e: Exception) {
        notificationDto.date
    } ?: ""
}