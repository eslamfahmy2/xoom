package com.chuify.xoomclient.domain.mapper

import com.chuify.xoomclient.data.remote.dto.LocationDto
import com.chuify.xoomclient.domain.model.Location


class LocationDtoMapper : DomainMapper<LocationDto, Location> {

    override fun mapToDomainModel(model: LocationDto): Location {
        return Location(
            id = model.location_id,
            title = model.title,
            details = model.details,
            latitude = model.latitude,
            longitude = model.longitude
        )
    }


    fun mapFromDomainModel(domainModel: Location): LocationDto {
        return LocationDto(
            longitude = domainModel.longitude.toString(),
            latitude = domainModel.latitude.toString(),
            title = domainModel.title,
            details = domainModel.details,
            location_id = domainModel.id
        )
    }


}