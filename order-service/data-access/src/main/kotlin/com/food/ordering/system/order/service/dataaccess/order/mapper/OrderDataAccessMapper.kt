package com.food.ordering.system.order.service.dataaccess.order.mapper

import com.food.ordering.system.common.valueobject.CustomerId
import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.OrderId
import com.food.ordering.system.common.valueobject.ProductId
import com.food.ordering.system.common.valueobject.RestaurantId
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderAddressEntity
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderItemEntity
import com.food.ordering.system.order.service.domain.core.entity.Order
import com.food.ordering.system.order.service.domain.core.entity.OrderItem
import com.food.ordering.system.order.service.domain.core.entity.Product
import com.food.ordering.system.order.service.domain.core.valueobject.OrderItemId
import com.food.ordering.system.order.service.domain.core.valueobject.StreetAddress
import com.food.ordering.system.order.service.domain.core.valueobject.TrackingId
import org.springframework.stereotype.Component
import java.util.function.Consumer
import java.util.stream.Collectors

@Component
class OrderDataAccessMapper {
    fun orderToOrderEntity(order: Order): OrderEntity {
        val orderEntity =
            OrderEntity(
                id = order.id.getID(),
                customerId = order.customerId?.getID(),
                restaurantId = order.restaurantId?.getID(),
                trackingId = order.trackingId?.getID(),
                price = order.price?.amountAsBigDecimal,
                orderStatus = order.orderStatus,
                failureMessages = order.failureMessages.toString(),
                address = deliveryAddressToAddressEntity(order.deliveryAddress),
                items = orderItemsToOrderItemEntities(order.orderItems),
            )

        orderEntity.address?.order = orderEntity
        orderEntity.items!!.forEach(
            Consumer { orderItemEntity: OrderItemEntity ->
                orderItemEntity.order = orderEntity
            },
        )

        return orderEntity
    }

    fun orderEntityToOrder(orderEntity: OrderEntity): Order {
        return Order(
            id = OrderId(orderEntity.id!!),
            customerId = CustomerId(orderEntity.customerId!!),
            restaurantId = RestaurantId(orderEntity.restaurantId!!),
            deliveryAddress = addressEntityToDeliveryAddress(orderEntity.address!!),
            price = Money(orderEntity.price!!),
            orderItems = orderItemEntitiesToOrderItems(orderEntity.items!!),
            trackingId = TrackingId(orderEntity.trackingId!!),
            orderStatus = orderEntity.orderStatus!!,
            failureMessages = listOf(orderEntity.failureMessages!!),
        )
    }

    fun orderItemEntitiesToOrderItems(items: List<OrderItemEntity>): List<OrderItem> {
        return items.stream()
            .map { it: OrderItemEntity ->
                OrderItem(
                    id = OrderItemId(it.id!!),
                    product = Product(ProductId(it.productId!!)),
                    price = Money.of(it.price!!),
                    quantity = it.quantity!!,
                    subTotal = Money.of(it.subTotal!!),
                )
            }.collect(Collectors.toList())
    }

    fun addressEntityToDeliveryAddress(address: OrderAddressEntity): StreetAddress {
        return StreetAddress(
            address.id!!,
            address.street!!,
            address.postalCode!!,
            address.city!!,
            address.country!!,
        )
    }

    fun deliveryAddressToAddressEntity(deliveryAddress: StreetAddress?): OrderAddressEntity {
        return if (deliveryAddress != null) {
            OrderAddressEntity(
                id = deliveryAddress.id,
                street = deliveryAddress.street,
                postalCode = deliveryAddress.postalCode,
                city = deliveryAddress.city,
                country = deliveryAddress.country,
            )
        } else {
            OrderAddressEntity()
        }
    }

    fun orderItemsToOrderItemEntities(items: List<OrderItem>): List<OrderItemEntity> {
        return items.stream().map { it: OrderItem ->
            OrderItemEntity(
                id = it.getID().getID(),
                productId = it.product?.id?.getID(),
                price = it.price?.amountAsBigDecimal,
                quantity = it.quantity,
                subTotal = it.subTotal?.amountAsBigDecimal,
            )
        }.collect(Collectors.toList())
    }
}
