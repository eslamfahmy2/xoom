package com.chuify.cleanxoomclient.presentation.ui.locations


sealed class LocationsIntent {

    object LoadLocations : LocationsIntent()
    object SaveAddress : LocationsIntent()

    object ShowDialog : LocationsIntent()
    object DismissDialog : LocationsIntent()

    data class TitleChange(val data: String) : LocationsIntent()
    data class DetailsChange(val data: String) : LocationsIntent()
    data class InstructionsChange(val data: String) : LocationsIntent()


}
