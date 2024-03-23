package com.food.ordering.system.order.service.domain.ports.output.repository

import com.food.ordering.system.common.valueobject.OrderId
import com.food.ordering.system.order.service.domain.core.entity.Order
import com.food.ordering.system.order.service.domain.core.valueobject.TrackingId

interface OrderRepository {
    fun save(order: Order): Order

    fun findById(orderId: OrderId): Order?

    fun findByTrackingId(trackingId: TrackingId): Order?
}
