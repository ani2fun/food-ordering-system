package com.food.ordering.system.order.service.domain.adapters.input.service.helpers

import com.food.ordering.system.order.service.domain.core.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class OrderCreateCommandHandler(
    val orderCreateHelper: OrderCreateHelper,
    val orderDataMapper: OrderDataMapper,
    val orderCreatedPaymentRequestMessagePublisher: OrderCreatedPaymentRequestMessagePublisher,
) {
    companion object {
        @JvmStatic
        val log: Logger by LoggerDelegate()
    }

    fun createOrder(createOrderCommand: CreateOrderCommand): CreateOrderResponse {
        val orderCreatedEvent: OrderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand)
        log.info(
            """Order persisted successfully with id: ${orderCreatedEvent.order.id.getID()},
                |proceeding to return Order Created Response.
            """.trimMargin(),
        )

        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent)
        val createOrderResponse = orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.order, "Order created successfully")

        log.info("Order Created Response: $createOrderResponse")
        return createOrderResponse
    }
}
