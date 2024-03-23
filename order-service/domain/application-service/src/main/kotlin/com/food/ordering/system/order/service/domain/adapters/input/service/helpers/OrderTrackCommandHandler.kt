package com.food.ordering.system.order.service.domain.adapters.input.service.helpers

import com.food.ordering.system.order.service.domain.core.exception.OrderNotFoundException
import com.food.ordering.system.order.service.domain.core.valueobject.TrackingId
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderTrackCommandHandler(
    val orderDataMapper: OrderDataMapper,
    val orderRepository: OrderRepository,
) {
    @Transactional(readOnly = true)
    fun trackOrder(trackOrderQuery: TrackOrderQuery): TrackOrderResponse {
        val orderResult = orderRepository.findByTrackingId(TrackingId(trackOrderQuery.trackingId))
        if (orderResult == null) {
            val msg = "Could not found the order with tracking id: ${trackOrderQuery.trackingId}"
            throw OrderNotFoundException(msg, IllegalStateException(msg))
        } else {
            return orderDataMapper.orderToTrackOrderResponse(orderResult)
        }
    }
}
