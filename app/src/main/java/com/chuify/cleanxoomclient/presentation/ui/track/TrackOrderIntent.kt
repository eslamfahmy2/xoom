package com.chuify.cleanxoomclient.presentation.ui.track


sealed class TrackOrderIntent {
    data class TrackOrder(val id: String) : TrackOrderIntent()
}
