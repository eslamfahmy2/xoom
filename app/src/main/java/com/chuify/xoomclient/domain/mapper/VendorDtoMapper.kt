package com.chuify.xoomclient.domain.mapper

import com.chuify.xoomclient.data.remote.dto.VendorDto
import com.chuify.xoomclient.domain.model.Vendor


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

    override fun mapFromDomainModel(domainModel: Vendor): VendorDto {
        TODO("Not yet implemented")
    }


}