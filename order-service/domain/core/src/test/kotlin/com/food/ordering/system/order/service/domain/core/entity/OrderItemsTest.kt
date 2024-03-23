package com.food.ordering.system.order.service.domain.core.entity

import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.OrderId
import com.food.ordering.system.common.valueobject.ProductId
import com.food.ordering.system.order.service.domain.core.entity.OrderValidation.orderItemsTotal
import com.food.ordering.system.order.service.domain.core.valueobject.OrderItemId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.UUID

class OrderItemsTest {

    @Test
    fun `Check Total Calculation for Order Items`() {
        // Create a list of OrderItems for testing
        val orderId1 = OrderId()
        val productId1 = ProductId(UUID.randomUUID())
        val product1 = Product(productId1, name = "Product 1", price = Money.of(BigDecimal(10.00)))
        val productId2 = ProductId(UUID.randomUUID())
        val product2 = Product(productId2, name = "Product 2", price = Money.of(BigDecimal(12.00)))

        val orderItems = listOf(
            OrderItem(
                id = OrderItemId(),
                orderId = orderId1,
                product = product1,
                quantity = 5,
                price = Money.of(BigDecimal(10.0)),
                subTotal = Money.of(BigDecimal(50.0))
            ),
            OrderItem(
                id = OrderItemId(),
                orderId = orderId1,
                product = product2,
                quantity = 5,
                price = Money.of(BigDecimal(12.0)),
                subTotal = Money.of(BigDecimal(60.0))
            )
        )

        // Calculate the total using the function
        val total = orderItemsTotal(orderItems)

        // Expected total is 50.0 + 60.0 = 110.0
        val expectedTotal = Money.of(BigDecimal(110.0))

        // Check if the calculated total matches the expected total
        assertEquals(expectedTotal, total)
    }

    @Test
    fun `Check Price Validation`() {
        // Create an OrderItem for testing
        val orderId1 = OrderId()
        val productPrice = Money.of(BigDecimal(10.0))
        val orderItem = OrderItem(
            id = OrderItemId(),
            orderId = orderId1,
            product = Product(
                id = ProductId(),
                name = "Product 1",
                price = productPrice
            ),
            quantity = 5,
            price = productPrice, // Price matches the product price
            subTotal = Money.of(BigDecimal(50.0)) // 10.0 * 5 = 50.0
        )

        // Call the isPriceValid function
        val isPriceValid = orderItem.isPriceValid()

        // Expected result is true because all conditions are met
        assertEquals(true, isPriceValid)
    }

    @Test
    fun `Check Price Validity with Invalid Price`() {
        // Create an OrderItem for testing with invalid conditions
        val orderId1 = OrderId()
        val productPrice = Money.of(BigDecimal(10.0))
        val orderItem = OrderItem(
            id = OrderItemId(),
            orderId = orderId1,
            product = Product(
                id = ProductId(),
                name = "Product 1",
                price = productPrice
            ),
            quantity = 5,
            price = Money.of(BigDecimal(12.0)),     // Price does not match the product price
            subTotal = Money.of(BigDecimal(60.0))   // 12.0 * 5 = 60.0
        )

        // Call the isPriceValid function
        val isPriceValid = orderItem.isPriceValid()

        // Expected result is false because the price is not valid
        assertEquals(false, isPriceValid)
    }
}
