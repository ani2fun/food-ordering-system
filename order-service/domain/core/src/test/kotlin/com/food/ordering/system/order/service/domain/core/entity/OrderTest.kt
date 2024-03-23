package com.food.ordering.system.order.service.domain.core.entity

import com.food.ordering.system.common.valueobject.CustomerId
import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.OrderId
import com.food.ordering.system.common.valueobject.ProductId
import com.food.ordering.system.common.valueobject.RestaurantId
import com.food.ordering.system.order.service.domain.core.valueobject.OrderItemId
import com.food.ordering.system.order.service.domain.core.valueobject.StreetAddress
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.UUID

class OrderTest {

    // THE ORDER OBJECT IS CREATED USING ORDER CONSTRUCTOR
    /**
     * In Domain-Driven Design (DDD), the concept of identity is crucial.
     * Entities are identified by their identity rather than their attributes.
     * This means that two entities with the same identity are considered equal, regardless of the differences in their other attributes.
     */
    @Test
    fun `Test for Inequality of Different Order ID Entities`() {
        // Create sample data for an Order with just orderId different.

        // GIVEN
        val deliveryAddress = StreetAddress(
            UUID.randomUUID(),
            "Street 1",
            "123",
            "City 1",
            "Country 1"
        )

        val product1 = Product(ProductId(), name = "Product 1", price = Money.of(BigDecimal(10.0)))
        val product2 = Product(ProductId(), name = "Product 2", price = Money.of(BigDecimal(12.0)))
        val orderItems = mutableListOf(
            OrderItem(
                id = OrderItemId(),
                product = product1,
                quantity = 5,
                price = Money.of(BigDecimal(10.0)),
                subTotal = Money.of(BigDecimal(50.0))
            ),
            OrderItem(
                id = OrderItemId(),
                product = product2,
                quantity = 5,
                price = Money.of(BigDecimal(12.0)),
                subTotal = Money.of(BigDecimal(60.0))
            )
        )
        val customerIdSame = CustomerId()
        val restaurantIdSame = RestaurantId()
        val price = Money.of(BigDecimal(110.0))

        // WHEN Order
        val order1 = Order(id = OrderId())
        val order2 = Order(id = OrderId())

        // Assign similar values to order1 and order2 fields.
        order1.customerId = customerIdSame
        order1.restaurantId = restaurantIdSame
        order1.deliveryAddress = deliveryAddress
        order1.price = price
        order1.orderItems = orderItems

        order2.customerId = customerIdSame
        order2.restaurantId = restaurantIdSame
        order2.deliveryAddress = deliveryAddress
        order2.price = price
        order2.orderItems = orderItems

        // THEN => ORDERS NOT SUPPOSE TO BE EQUAL
        assertNotEquals(order1, order2)

        // ALL OTHER PROPERTIES ARE MUST BE EQUAL
        assertEquals(order1.customerId, order2.customerId)
        assertEquals(order1.restaurantId, order2.restaurantId)
        assertEquals(order1.deliveryAddress, order2.deliveryAddress)
        assertEquals(order1.orderItems, order2.orderItems)
    }

    @Test
    fun `Test for Similar Order ID Entities with Different Other Parameters Being Equal`() {
        // Create sample data for an Order with just orderId different.

        // GIVEN
        val deliveryAddress = StreetAddress(
            UUID.randomUUID(),
            "Street 1",
            "123",
            "City 1",
            "Country 1"
        )

        val product1 = Product(ProductId(), name = "Product 1", price = Money.of(BigDecimal(10.0)))
        val product2 = Product(ProductId(), name = "Product 2", price = Money.of(BigDecimal(12.0)))
        val orderItems = mutableListOf(
            OrderItem(
                id = OrderItemId(),
                product = product1,
                quantity = 5,
                price = Money.of(BigDecimal(10.0)),
                subTotal = Money.of(BigDecimal(50.0))
            ),
            OrderItem(
                id = OrderItemId(),
                product = product2,
                quantity = 5,
                price = Money.of(BigDecimal(12.0)),
                subTotal = Money.of(BigDecimal(60.0))
            )
        )
        val price = Money.of(BigDecimal(110.0))

        // WHEN Order
        val orderIdSame = OrderId()
        val order1 = Order(id = orderIdSame)
        val order2 = Order(id = orderIdSame)

        // Assign similar values to order1 and order2 fields.
        order1.customerId = CustomerId()
        order1.restaurantId = RestaurantId()
        order1.deliveryAddress = deliveryAddress
        order1.price = price
        order1.orderItems = orderItems

        order2.customerId = CustomerId()
        order2.restaurantId = RestaurantId()
        order2.deliveryAddress = deliveryAddress
        order2.price = price
        order2.orderItems = orderItems


        // SOME PROPERTIES MAY OR MAY NOT BE EQUAL
        assertNotEquals(order1.customerId, order2.customerId)
        assertNotEquals(order1.restaurantId, order2.restaurantId)
        assertEquals(order1.deliveryAddress, order2.deliveryAddress)
        assertEquals(order1.orderItems, order2.orderItems)

        // THEN => ORDERS SUPPOSE TO BE EQUAL
        assertEquals(order1, order2)
    }

}
