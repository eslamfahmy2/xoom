package com.chuify.cleanxoomclient.domain.mapper

import com.chuify.cleanxoomclient.data.remote.dto.VendorDto
import com.chuify.cleanxoomclient.domain.model.Vendor


class VendorDtoMapper : DomainMapper<VendorDto, Vendor> {

    override fun mapToDomainModel(model: VendorDto): Vendor {
        return Vendor(
            id = model.vendor_id!!,
            name = model.vendor_name!!,
            image = model.image!!

        )
    }

    fun toDomainList(initial: List<VendorDto>): List<Vendor> {
        return initial.map { mapToDomainModel(it) }
    }

    fun mapFromDomainModel(domainModel: Vendor): VendorDto {
        return VendorDto(vendor_id = domainModel.id,
            vendor_name = domainModel.name,
            image = domainModel.image
        )
    }


}