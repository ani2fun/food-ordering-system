package com.food.ordering.system.order.service.domain.core

import com.food.ordering.system.common.DomainConstants
import com.food.ordering.system.order.service.domain.core.entity.Order
import com.food.ordering.system.order.service.domain.core.entity.OrderItem
import com.food.ordering.system.order.service.domain.core.entity.Restaurant
import com.food.ordering.system.order.service.domain.core.event.OrderCancelledEvent
import com.food.ordering.system.order.service.domain.core.event.OrderCreatedEvent
import com.food.ordering.system.order.service.domain.core.event.OrderPaidEvent
import com.food.ordering.system.order.service.domain.core.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import org.slf4j.Logger
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * The `OrderDomainServiceImpl` class is responsible for implementing the `IOrderDomainService` interface,
 * managing various operations related to orders within the domain.
 *
 * Explanation and Key Points:
 * - Within order items, there is a reference to products, where each product entity contains a name and price.
 *
 * - The inclusion of the product entity in the order item is designed to verify the actual price of a product.
 *
 * - While the product ID and item price are received from the client, this class ensures that the
 *   provided price matches the actual product price. To achieve this, it retrieves product information from
 *   the database using the restaurant entity and crosschecks the details.
 *
 * - The implementation of this process follows the validation of the restaurant, ensuring that the business
 *   requirements are met by utilizing multiple aggregates, such as orders, products, and restaurants.
 *
 * In summary, the `OrderDomainServiceImpl` class plays a vital role in handling various aspects of order
 * management within the domain, which includes verification of product prices and validation of restaurant
 * details using multiple aggregates to meet the business requirements.
 */

class OrderDomainServiceImpl : IOrderDomainService {
    private val zoneId = ZoneId.of(DomainConstants.UTC)

    companion object {
        @JvmStatic
        val log: Logger by LoggerDelegate()
    }

    private fun validateRestaurant(restaurant: Restaurant) {
        restaurant.active?.let {
            if (!it) {
                val msg = "Restaurant with id " + restaurant.getID() + " is currently not active!"
                throw OrderDomainException(msg, IllegalStateException(msg))
            }
        }
    }

    fun setOrderProductInformation(
        order: Order,
        restaurant: Restaurant,
    ) {
        val productMap = restaurant.products?.associateBy { it.getID() } // Create a mapping of product IDs to products

        order.orderItems.forEach { orderItem: OrderItem ->

            val currentProduct = orderItem.product

            val restaurantProduct = productMap?.get(currentProduct?.getID())

            if (restaurantProduct != null) {
                log.info(
                    """The current product (ID: ${currentProduct!!.getID()}) matches the restaurant product (ID: ${restaurantProduct.getID()}).
                        |Proceeding with updating the name and price of the current product.
                    """.trimMargin(),
                )

                currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.name!!, restaurantProduct.price!!)

                // TODO: may be update here is not needed.
                orderItem.subTotal = orderItem.quantity?.let { restaurantProduct.price!!.multiply(it) }
                orderItem.price = restaurantProduct.price!!
            } else {
                log.info(
                    """
                    The current product (ID: ${currentProduct!!.getID()}) does not match any restaurant product.
                    Skipping the update for the current product's name and price.
                    """.trimIndent(),
                )
            }
        }
    }

    override fun validateAndInitiateOrder(
        order: Order,
        restaurant: Restaurant,
    ): OrderCreatedEvent {
        validateRestaurant(restaurant)
        setOrderProductInformation(order, restaurant)
        order.validateAndFinaliseOrder()
        log.info("Order with id: {} is initiated!", order.getID())
        return OrderCreatedEvent(order, ZonedDateTime.now(zoneId))
    }

    override fun payOrder(order: Order): OrderPaidEvent {
        order.pay()
        log.info("Order with id: {} is approved", order.getID())
        return OrderPaidEvent(order, ZonedDateTime.now(zoneId))
    }

    override fun approveOrder(order: Order) {
        order.approve()
        log.info("Order with id: {} is approved", order.getID())
    }

    override fun cancelOrderPayment(
        order: Order,
        failureMessages: List<String>,
    ): OrderCancelledEvent {
        order.initCancel(failureMessages)
        log.info("Order payment is cancelling for order id: {}", order.getID())
        return OrderCancelledEvent(order, ZonedDateTime.now(zoneId))
    }

    override fun cancelOrder(
        order: Order,
        failureMessages: List<String>,
    ) {
        order.cancel(failureMessages)
        log.info("Order with id: {} is cancelled", order.getID())
    }
}
