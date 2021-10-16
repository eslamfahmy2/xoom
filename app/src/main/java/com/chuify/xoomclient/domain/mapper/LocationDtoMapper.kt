package com.chuify.xoomclient.domain.mapper

import com.chuify.xoomclient.data.remote.dto.LocationDto
import com.chuify.xoomclient.domain.model.Location


class LocationDtoMapper : DomainMapper<LocationDto, Location> {

    override fun mapToDomainModel(model: LocationDto): Location {
        return Location(
            address = model.address_url!!,
            details = model.details!!,
            instructions = model.instructions!!,
            latitude = model.latitude!!.toDouble(),
            longitude = model.longitude!!.toDouble()
        )
    }


     fun mapFromDomainModel(domainModel: Location): LocationDto {
        return LocationDto(
            longitude = domainModel.longitude.toString(),
            latitude = domainModel.latitude.toString(),
            instructions = domainModel.instructions,
            details = domainModel.details,
            address_url = domainModel.address
        )
    }


}