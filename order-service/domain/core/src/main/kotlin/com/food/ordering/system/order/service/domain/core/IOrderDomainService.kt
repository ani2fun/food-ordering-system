package com.food.ordering.system.order.service.domain.core

import com.food.ordering.system.order.service.domain.core.entity.Order
import com.food.ordering.system.order.service.domain.core.entity.Restaurant
import com.food.ordering.system.order.service.domain.core.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.core.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.core.event.OrderPaidEvent

/**
 * General explanation for domain service interfaces:
 *
 * The `IOrderDomainService` interface defines methods for managing orders within the domain.
 * It handles various aspects of the order lifecycle, including validation, initiation, payment processing,
 * approval, and cancellation.
 *
 * Key points:
 * - The methods within this interface return domain events such as `OrderCreatedEvent`, `OrderPaidEvent`,
 *   and `OrderCancelledEvent`, which will be handled by the application service.
 *
 * - The approach here is to create and return domain events within the domain service, while the actual
 *   event firing process occurs in the application service.
 *
 * - This separation is crucial to ensure that the event firing happens after the successful persistence of
 *   underlying business operations into the database.
 *
 * - The domain service's primary responsibility is to encapsulate complex business logic, especially logic
 *   that doesn't fit neatly into any entity class or requires access to multiple aggregates.
 *
 * - By delegating repository calls to the application service, the domain core module remains dedicated to
 *   implementing and maintaining business logic, preventing it from being cluttered with event publishing
 *   or tracking concerns.
 *
 * In summary, the `IOrderDomainService` serves as an intermediary layer between the domain core, where business
 * logic resides, and the application service, which manages event handling. This separation ensures that
 * domain events are correctly created and handled in a structured manner, supporting a clean and organized
 * Domain-Driven Design approach.
 */

interface IOrderDomainService {
    /**
     * This method validates and initiates an order by calling the necessary business methods from the provided
     * order and restaurant entities. It takes the order and restaurant entities as parameters and returns an
     * OrderCreatedEvent.
     */
    fun validateAndInitiateOrder(order: Order, restaurant: Restaurant): OrderCreatedEvent

    fun payOrder(order: Order): OrderPaidEvent

    fun approveOrder(order: Order)

    fun cancelOrderPayment(order: Order, failureMessages: List<String>): OrderCancelledEvent

    fun cancelOrder(order: Order, failureMessages: List<String>)

}