package com.chuify.xoomclient.domain.mapper

import com.chuify.xoomclient.data.remote.dto.ProductDto
import com.chuify.xoomclient.domain.model.Product


class ProductDtoMapper : DomainMapper<ProductDto, Product> {

    override fun mapToDomainModel(model: ProductDto): Product {
        return Product(
            id = model.product_id!!,
            name = model.product_name!!,
            image = model.image!!

        )
    }

    fun toDomainList(initial: List<ProductDto>): List<Product> {
        return initial.map { mapToDomainModel(it) }
    }

    override fun mapFromDomainModel(domainModel: Product): ProductDto {
        return ProductDto(
            product_id = domainModel.id,
            product_name = domainModel.name,
            image = domainModel.image
        )
    }


}