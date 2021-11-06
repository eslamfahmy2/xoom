package com.chuify.xoomclient.domain.mapper

import com.chuify.xoomclient.data.remote.dto.OrderDto
import com.chuify.xoomclient.domain.model.Order

class OrderDtoMapper : DomainMapper<OrderDto, Order> {

    override fun mapToDomainModel(model: OrderDto): Order {
        return Order(
            id = model.order_id!!,
            size = model.orderdetails!!.size.toString(),
            price = model.totalprice.toString(),
            image = model.orderdetails.first().image!!,
            name = model.orderdetails.first().product_name!!,
            refill = model.orderdetails.first().refill_new!!,
            status = model.orderstatus!!,
            products = model.orderdetails ,
            locationID = model.location_id!! ,
            paymentMethod = model.paymentMethod!! ,
            totalPrice = model.totalprice!!

        )
    }


}