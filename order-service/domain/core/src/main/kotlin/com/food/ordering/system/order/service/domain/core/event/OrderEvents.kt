package com.food.ordering.system.order.service.domain.core.event

import com.food.ordering.system.common.event.DomainEvent
import com.food.ordering.system.order.service.domain.core.entity.Order
import java.time.ZonedDateTime

abstract class OrderEvent(open val order: Order, open val createdAt: ZonedDateTime) : DomainEvent<Order>

class OrderCreatedEvent(
    order: Order,
    createdAt: ZonedDateTime,
) : OrderEvent(order, createdAt) {

    override fun fire() {
        TODO("Not yet implemented")
    }
}

class OrderPaidEvent(
    order: Order,
    createdAt: ZonedDateTime
) : OrderEvent(order, createdAt) {
    override fun fire() {
        TODO("Not yet implemented")
    }
}

class OrderCancelledEvent(
    order: Order,
    createdAt: ZonedDateTime
) : OrderEvent(order, createdAt) {
    override fun fire() {
        TODO("Not yet implemented")
    }
}
