package com.food.ordering.system.order.service.domain.ports.input.service

import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import jakarta.validation.Valid

/*
https://beanvalidation.org/1.1/spec/
When defining method constraints within inheritance hierarchies (that is, class inheritance by extending base classes and interface inheritance by implementing or extending interfaces) one has to obey the Liskov substitution principle which mandates that:
a method's preconditions (as represented by parameter constraints) must not be strengthened in sub types
a method's postconditions (as represented by return value constraints) must not be weakened in sub types
*/

/**
 * This is the OrderApplicationService interface, which will be utilized by clients of the application.
 * It serves as a point of entry for initiating orders, similar to the way a postman makes calls to initiate orders.
 */

interface OrderApplicationService {
    fun createOrder(
        @Valid createOrderCommand: CreateOrderCommand,
    ): CreateOrderResponse

    fun trackOrder(
        @Valid trackOrderQuery: TrackOrderQuery,
    ): TrackOrderResponse
}
