package com.chuify.xoomclient.presentation.ui.accessoryDetails

import com.chuify.xoomclient.domain.model.Accessory


sealed class AccessoryDetailsIntent {

    data class OpenAccessoryPreview(val accessory: Accessory) : AccessoryDetailsIntent()
    object DismissAcDetails : AccessoryDetailsIntent()
    data class IncreaseAccessoryCart(val accessory: Accessory) : AccessoryDetailsIntent()
    data class DecreaseAccessoryCart(val accessory: Accessory) : AccessoryDetailsIntent()
}
