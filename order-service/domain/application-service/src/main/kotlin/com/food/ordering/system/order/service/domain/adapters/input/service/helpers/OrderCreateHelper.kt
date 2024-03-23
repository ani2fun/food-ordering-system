package com.food.ordering.system.order.service.domain.adapters.input.service.helpers

import com.food.ordering.system.order.service.domain.core.IOrderDomainService
import com.food.ordering.system.order.service.domain.core.entity.Customer
import com.food.ordering.system.order.service.domain.core.entity.Order
import com.food.ordering.system.order.service.domain.core.entity.Restaurant
import com.food.ordering.system.order.service.domain.core.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.core.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository
import org.slf4j.Logger
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
class OrderCreateHelper(
    private val orderDomainService: IOrderDomainService,
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val restaurantRepository: RestaurantRepository,
    private val orderDataMapper: OrderDataMapper,
) {
    companion object {
        val log: Logger by LoggerDelegate()
    }

    @Transactional
    fun persistOrder(createOrderCommand: CreateOrderCommand): OrderCreatedEvent {
        val customer: Customer = checkCustomer(createOrderCommand.customerId)
        log.info("Customer found: ${customer.id.getID()}.")
        val restaurant: Restaurant = checkRestaurant(createOrderCommand)

        val order: Order = orderDataMapper.createOrderCommandToDomainOrder(createOrderCommand)

        val orderCreatedEvent: OrderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant)
        log.info("Order created event: $orderCreatedEvent.")

        val savedOrder: Order = saveOrder(order)
        log.info("Order saved with id: ${savedOrder.id.getID()}, proceeding to return Order Created Response.")

        return orderCreatedEvent
    }

    fun checkCustomer(customerId: UUID): Customer {
        val customer: Customer? = customerRepository.findCustomer(customerId = customerId)
        if (customer == null) {
            val msg = "Customer not found from customer repo for id: $customerId."
            log.error(msg)
            throw OrderDomainException(msg, IllegalStateException(msg))
        } else {
            log.info("Customer found from customer repo for id: $customerId.")
            return customer
        }
    }

    fun saveOrder(order: Order): Order {
        try {
            val orderResult: Order = orderRepository.save(order)
            log.info("Order is saved with id: {}", orderResult.id.getID())
            return orderResult
        } catch (ex: Exception) {
            val msg = "Could not save order!"
            log.error(msg)
            throw OrderDomainException(msg, IllegalStateException(msg))
        }
    }

    fun checkRestaurant(createOrderCommand: CreateOrderCommand): Restaurant {
        val restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)
        val optRestaurant: Restaurant? = restaurantRepository.findRestaurantInformation(restaurant)
        if (optRestaurant == null) {
            log.info("Could not find restaurant with restaurant id: {}", createOrderCommand.restaurantId)
            val msg = "Could not find restaurant with restaurant id: ${createOrderCommand.restaurantId}"
            throw OrderDomainException(msg, IllegalStateException(msg))
        } else {
            return Restaurant(optRestaurant.getID(), optRestaurant.products, optRestaurant.active)
        }
    }
}
