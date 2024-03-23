package com.food.ordering.system.order.service.controllers

import com.food.ordering.system.order.service.api.v1.rest.OrderServiceApiDelegate
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(value = ["/order-service/v1/orders"], produces = ["application/vnd.api.v1+json"])
class OrderController(private val orderApplicationService: OrderApplicationService) : OrderServiceApiDelegate {
    companion object {
        val log by LoggerDelegate()
    }

    @PostMapping
    fun createOrder(
        @RequestBody createOrderCommand: CreateOrderCommand,
    ): ResponseEntity<CreateOrderResponse> {
        log.info(
            "Creating order for customer: {} at restaurant: {}",
            createOrderCommand.customerId,
            createOrderCommand.restaurantId,
        )
        val createOrderResponse = orderApplicationService.createOrder(createOrderCommand)
        log.info("Order created with tracking id: {}", createOrderResponse.trackingId)
        return ResponseEntity.ok(createOrderResponse)
    }

    @GetMapping("/{trackingId}")
    fun getOrderByTrackingId(
        @PathVariable trackingId: UUID,
    ): ResponseEntity<TrackOrderResponse> {
        val trackOrderResponse = orderApplicationService.trackOrder(TrackOrderQuery(trackingId))
        log.info("Returning order status with tracking id: {}", trackOrderResponse.trackingId)
        return ResponseEntity.ok(trackOrderResponse)
    }
}
