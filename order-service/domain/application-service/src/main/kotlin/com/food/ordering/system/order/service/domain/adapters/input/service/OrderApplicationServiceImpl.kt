package com.food.ordering.system.order.service.domain.adapters.input.service

import com.food.ordering.system.order.service.domain.adapters.input.service.helpers.OrderCreateCommandHandler
import com.food.ordering.system.order.service.domain.adapters.input.service.helpers.OrderTrackCommandHandler
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class OrderApplicationServiceImpl(
    val orderCreateCommandHandler: OrderCreateCommandHandler,
    val orderTrackCommandHandler: OrderTrackCommandHandler,
) : OrderApplicationService {
    override fun createOrder(createOrderCommand: CreateOrderCommand): CreateOrderResponse {
        return orderCreateCommandHandler.createOrder(createOrderCommand)
    }

    override fun trackOrder(trackOrderQuery: TrackOrderQuery): TrackOrderResponse {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery)
    }
}
