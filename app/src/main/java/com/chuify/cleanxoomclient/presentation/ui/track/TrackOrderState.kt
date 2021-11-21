package com.chuify.cleanxoomclient.presentation.ui.track

import com.chuify.cleanxoomclient.data.remote.dto.TrackDto


sealed class TrackOrderState {
    data class Success(val trackData: TrackDto) : TrackOrderState()
    data class Error(val message: String? = null) : TrackOrderState()
    object Loading : TrackOrderState()
}