package com.chuify.cleanxoomclient.domain.mapper

import com.chuify.cleanxoomclient.data.remote.dto.ProductDto
import com.chuify.cleanxoomclient.domain.model.Product


class ProductDtoMapper : DomainMapper<ProductDto, Product> {

    override fun mapToDomainModel(model: ProductDto): Product {
        return Product(
            id = model.product_id!!,
            name = model.product_name!!,
            image = model.image!!,
            price = model.selling_price!!.toDouble(),
            refill = model.refill_new!!,
            size = model.product_size!!,
        )
    }

    fun toDomainList(initial: List<ProductDto>): List<Product> {
        return initial.map { mapToDomainModel(it) }
    }


}