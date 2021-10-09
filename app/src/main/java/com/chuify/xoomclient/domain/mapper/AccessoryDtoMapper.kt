package com.chuify.xoomclient.domain.mapper

import com.chuify.xoomclient.data.remote.dto.AccessoryDto
import com.chuify.xoomclient.domain.model.Accessory


class AccessoryDtoMapper : DomainMapper<AccessoryDto, Accessory> {

    override fun mapToDomainModel(model: AccessoryDto): Accessory {
        return Accessory(
            id = model.product_id!!,
            name = model.product_name!!,
            image = model.image!!

        )
    }

    fun toDomainList(initial: List<AccessoryDto>): List<Accessory> {
        return initial.map { mapToDomainModel(it) }
    }

    override fun mapFromDomainModel(domainModel: Accessory): AccessoryDto {
        return AccessoryDto(
            product_id = domainModel.id,
            product_name = domainModel.name,
            image = domainModel.image
        )
    }


}