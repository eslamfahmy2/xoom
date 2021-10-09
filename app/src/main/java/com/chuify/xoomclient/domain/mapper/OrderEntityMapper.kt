package com.chuify.xoomclient.domain.mapper

import com.chuify.xoomclient.data.local.entity.OrderEntity
import com.chuify.xoomclient.domain.model.Order
import com.chuify.xoomclient.domain.model.Product


class OrderEntityMapper : DomainMapper<OrderEntity, Order> {

    override fun mapToDomainModel(model: OrderEntity): Order {
        return Order(
            price = model.price,
            image = model.image,
            name = model.name,
            id = model.id,
            basePrice = model.basePrice,
            quantity = model.quantity,
            time = model.time
        )
    }

    fun toDomainList(initial: List<OrderEntity>): List<Order> {
        return initial.map { mapToDomainModel(it) }
    }

    override fun mapFromDomainModel(domainModel: Order): OrderEntity {
        return OrderEntity(
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