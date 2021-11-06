package com.chuify.xoomclient.presentation.ui.locations

import com.chuify.xoomclient.domain.model.Location


sealed class LocationsState {
    data class Success(val locations: List<Location>) : LocationsState()
    data class Error(val message: String? = null) : LocationsState()
    object Loading : LocationsState()
}