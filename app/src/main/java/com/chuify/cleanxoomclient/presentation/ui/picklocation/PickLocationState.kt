package com.chuify.cleanxoomclient.presentation.ui.picklocation

import com.chuify.cleanxoomclient.domain.model.Location


sealed class PickLocationState {
    data class Success(val locations: List<Location>) : PickLocationState()
    data class Error(val message: String? = null) : PickLocationState()
    object Loading : PickLocationState()
}