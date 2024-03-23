package com.food.ordering.system.order.service.domain.mapper

import com.food.ordering.system.common.valueobject.CustomerId
import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.OrderId
import com.food.ordering.system.common.valueobject.ProductId
import com.food.ordering.system.common.valueobject.RestaurantId
import com.food.ordering.system.order.service.domain.core.entity.Order
import com.food.ordering.system.order.service.domain.core.entity.OrderItem
import com.food.ordering.system.order.service.domain.core.entity.Product
import com.food.ordering.system.order.service.domain.core.entity.Restaurant
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.core.valueobject.StreetAddress
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class OrderDataMapper {
    companion object {
        @JvmStatic
        val logger: Logger by LoggerDelegate()
    }

    fun createOrderCommandToRestaurant(createOrderCommand: CreateOrderCommand): Restaurant {
        return Restaurant(
            id = RestaurantId(createOrderCommand.restaurantId),
            products =
                createOrderCommand.items.map { orderItem ->
                    Product(ProductId(orderItem.productId))
                }.toList(),
        )
    }

    fun createOrderCommandToDomainOrder(createOrderCommand: CreateOrderCommand): Order {
        val order =
            Order(
                customerId = CustomerId(createOrderCommand.customerId),
                restaurantId = RestaurantId(createOrderCommand.restaurantId),
                deliveryAddress = orderAddressToStreetAddress(createOrderCommand.address),
                price = Money.of(createOrderCommand.price),
            )

        order.orderItems = orderItemsToOrderItemEntities(order.id, createOrderCommand.items)

        logger.info("Mapped domain Order is : $order")
        return order
    }

    private fun orderItemsToOrderItemEntities(
        orderId: OrderId,
        orderItems: List<com.food.ordering.system.order.service.domain.dto.create.OrderItem>,
    ): MutableList<OrderItem> {
        return orderItems.map { item ->
            OrderItem(
                orderId = orderId,
                product = Product(id = ProductId(item.productId), price = Money.of(item.price)),
                quantity = item.quantity,
                price = Money.of(item.price),
                subTotal = Money.of(item.subTotal),
            )
        }.toMutableList()
    }

    private fun orderAddressToStreetAddress(orderAddress: OrderAddress): StreetAddress {
        return StreetAddress(
            street = orderAddress.street,
            postalCode = orderAddress.postalCode,
            city = orderAddress.city,
            country = orderAddress.country,
        )
    }

    fun orderToCreateOrderResponse(
        order: Order,
        message: String,
    ): CreateOrderResponse {
        val trackingId = order.trackingId?.getID()!!
        val orderStatus = order.orderStatus!!

        return CreateOrderResponse(
            trackingId = trackingId,
            orderStatus = orderStatus,
            message = message,
        )
    }

    fun orderToTrackOrderResponse(order: Order?): TrackOrderResponse {
        val trackingId = order?.trackingId?.getID()!!
        val orderStatus = order.orderStatus!!
        val messages = order.failureMessages.toList()
        return TrackOrderResponse(
            trackingId = trackingId,
            orderStatus = orderStatus,
            failureMessages = messages,
        )
    }
}
