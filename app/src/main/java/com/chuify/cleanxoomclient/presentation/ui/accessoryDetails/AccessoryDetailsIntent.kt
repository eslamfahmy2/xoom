package com.chuify.cleanxoomclient.presentation.ui.accessoryDetails

import com.chuify.cleanxoomclient.domain.model.Accessory


sealed class AccessoryDetailsIntent {

    data class OpenAccessoryPreview(val accessory: Accessory) : AccessoryDetailsIntent()
    object DismissAcDetails : AccessoryDetailsIntent()
    data class IncreaseAccessoryCart(val accessory: Accessory) : AccessoryDetailsIntent()
    data class DecreaseAccessoryCart(val accessory: Accessory) : AccessoryDetailsIntent()
}
