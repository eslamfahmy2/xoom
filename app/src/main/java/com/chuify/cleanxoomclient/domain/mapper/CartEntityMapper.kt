package com.chuify.cleanxoomclient.domain.mapper

import com.chuify.cleanxoomclient.data.local.entity.CartEntity
import com.chuify.cleanxoomclient.domain.model.Cart


class CartEntityMapper : DomainMapper<CartEntity, Cart> {

    override fun mapToDomainModel(model: CartEntity): Cart {
        return Cart(
            price = model.price,
            image = model.image,
            name = model.name,
            id = model.id,
            basePrice = model.basePrice,
            quantity = model.quantity,
            time = model.time
        )
    }

    fun toDomainList(initial: List<CartEntity>): List<Cart> {
        return initial.map { mapToDomainModel(it) }
    }

    fun mapFromDomainModel(domainModel: Cart): CartEntity {
        return CartEntity(
            price = domainModel.price,
            image = domainModel.image,
            name = domainModel.name,
            id = domainModel.id,
            basePrice = domainModel.basePrice,
            quantity = domainModel.quantity,
            time = domainModel.time
        )
    }


}