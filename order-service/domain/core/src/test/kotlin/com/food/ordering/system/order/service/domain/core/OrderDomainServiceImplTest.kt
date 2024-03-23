package com.food.ordering.system.order.service.domain.core

import com.food.ordering.system.common.valueobject.CustomerId
import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.OrderId
import com.food.ordering.system.common.valueobject.ProductId
import com.food.ordering.system.common.valueobject.RestaurantId
import com.food.ordering.system.order.service.domain.core.entity.Order
import com.food.ordering.system.order.service.domain.core.entity.OrderItem
import com.food.ordering.system.order.service.domain.core.entity.Product
import com.food.ordering.system.order.service.domain.core.entity.Restaurant
import com.food.ordering.system.order.service.domain.core.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.core.valueobject.OrderItemId
import com.food.ordering.system.order.service.domain.core.valueobject.StreetAddress
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.UUID

class OrderDomainServiceImplTest {
    @Test
    fun `Test for setOrderProductInformation`() {
        // Create sample data for an Order with just orderId different.
        val orderId1 = OrderId()
        val customerId1 = CustomerId()
        val restaurantId1 = RestaurantId()
        val deliveryAddress1 =
            StreetAddress(
                UUID.randomUUID(),
                "Street 1",
                "123",
                "City 1",
                "Country 1",
            )

        val productIdSame = ProductId()

        // Create some sample products for the restaurant
        val productFromOrder = Product(productIdSame/*, "Product 1", Money.of(BigDecimal(10.0))*/)
        val orderItemFromOrder =
            OrderItem(
                id = OrderItemId(),
                orderId = orderId1,
                product = productFromOrder,
                quantity = 5,
//            price = Money.of(BigDecimal(10.0)),
//            subTotal = Money.of(BigDecimal(50.0))
            )
        val orderPriceFromOrder = Money.of(BigDecimal(50.0))

        // Product is taken from Restaurant for the same product ID.

        val productFromRestaurant = Product(productIdSame, "Product from Restaurant", Money.of(BigDecimal(11.00)))
        // Restaurant's products
        val restaurantId = RestaurantId()
        val restaurant =
            Restaurant(
                id = restaurantId,
                products = mutableListOf(productFromRestaurant),
                active = true,
            )

        val order =
            Order(
                id = orderId1,
                customerId = customerId1,
                restaurantId = restaurantId1,
                deliveryAddress = deliveryAddress1,
                price = orderPriceFromOrder,
                orderItems = mutableListOf(orderItemFromOrder),
            )

        val orderProductBeforeUpdate = order.orderItems.find { item -> item.product?.id == productIdSame }?.product
        val restaurantProductBeforeUpdate = restaurant.products?.find { product -> product.id == productIdSame }
        assertNotEquals(orderProductBeforeUpdate?.price, restaurantProductBeforeUpdate?.price)
        assertNotEquals(orderProductBeforeUpdate?.name, restaurantProductBeforeUpdate?.name)

        // Call the function being tested
        OrderDomainServiceImpl().setOrderProductInformation(order, restaurant)

        val orderProductAfterUpdate = order.orderItems.find { item -> item.product?.id == productIdSame }?.product
        val restaurantProductAfterUpdate = restaurant.products?.find { product -> product.id == productIdSame }

        assertEquals(orderProductAfterUpdate?.price, restaurantProductAfterUpdate?.price)
        assertEquals(orderProductAfterUpdate?.name, restaurantProductAfterUpdate?.name)
    }

    @Test
    fun `Test for validate And Initiate Order`() {
        // Create sample data for an Order with just orderId different.
        val orderId1 = OrderId()
        val customerId1 = CustomerId()
        val restaurantId1 = RestaurantId()
        val deliveryAddress1 =
            StreetAddress(
                UUID.randomUUID(),
                "Street 1",
                "123",
                "City 1",
                "Country 1",
            )

        val productIdSame = ProductId()

        // Create some sample products for the restaurant
        val productFromOrder = Product(productIdSame) // Product(productIdSame, "Product 1", Money.of(BigDecimal(10.0)))
        val orderItemFromOrder =
            OrderItem(
                id = OrderItemId(),
                orderId = orderId1,
                product = productFromOrder,
                quantity = 5, // , price = Money.of(BigDecimal(10.0)), subTotal = Money.of(BigDecimal(50.0))
            )

        val orderPriceFromOrder = Money.of(BigDecimal(55.0))
        val order =
            Order(
                id = orderId1,
                customerId = customerId1,
                restaurantId = restaurantId1,
                deliveryAddress = deliveryAddress1,
                price = orderPriceFromOrder,
                orderItems = mutableListOf(orderItemFromOrder),
            )

        // Product is taken from Restaurant for the same product ID.

        val productFromRestaurant = Product(productIdSame, "Product from Restaurant", Money.of(BigDecimal(11.00)))
        // Restaurant's products
        val restaurantId = RestaurantId()
        val restaurant =
            Restaurant(
                id = restaurantId,
                products = mutableListOf(productFromRestaurant),
                active = true,
            )

        val orderBeforeValidate = order.orderItems.find { item -> item.product?.id == productIdSame }?.product
        val restaurantProductBeforeUpdate = restaurant.products?.find { product -> product.id == productIdSame }
        assertNotEquals(orderBeforeValidate?.price, restaurantProductBeforeUpdate?.price)
        assertNotEquals(orderBeforeValidate?.name, restaurantProductBeforeUpdate?.name)

        // Call the function being tested
        OrderDomainServiceImpl().validateAndInitiateOrder(order, restaurant)

        val orderAfterValidate = order.orderItems.find { item -> item.product?.id == productIdSame }?.product
        val restaurantProductAfterUpdate = restaurant.products?.find { product -> product.id == productIdSame }

        assertEquals(orderAfterValidate?.price, restaurantProductAfterUpdate?.price)
        assertEquals(orderAfterValidate?.name, restaurantProductAfterUpdate?.name)
    }

    @Test
    fun `When Order Price is Different than total Order Items Price Throw OrderDomainException`() {
        // Create sample data for an Order with just orderId different.
        val orderId1 = OrderId()
        val customerId1 = CustomerId()
        val restaurantId1 = RestaurantId()
        val deliveryAddress1 =
            StreetAddress(
                UUID.randomUUID(),
                "Street 1",
                "123",
                "City 1",
                "Country 1",
            )

        val productIdSame = ProductId()

        // Create some sample products for the restaurant
        val productFromOrder = Product(productIdSame) // Product(productIdSame, "Product 1", Money.of(BigDecimal(10.0)))
        val orderItemFromOrder =
            OrderItem(
                id = OrderItemId(),
                orderId = orderId1,
                product = productFromOrder,
                quantity = 5, // , price = Money.of(BigDecimal(10.0)), subTotal = Money.of(BigDecimal(50.0))
            )

        val orderPriceFromOrderIsDifferent = Money.of(BigDecimal(10.0))

        val order =
            Order(
                id = orderId1,
                customerId = customerId1,
                restaurantId = restaurantId1,
                deliveryAddress = deliveryAddress1,
                price = orderPriceFromOrderIsDifferent,
                orderItems = mutableListOf(orderItemFromOrder),
            )

        // Product is taken from Restaurant for the same product ID.

        val productFromRestaurant = Product(productIdSame, "Product from Restaurant", Money.of(BigDecimal(11.00)))
        // Restaurant's products
        val restaurantId = RestaurantId()
        val restaurant =
            Restaurant(
                id = restaurantId,
                products = mutableListOf(productFromRestaurant),
                active = true,
            )

        val orderBeforeValidate = order.orderItems.find { item -> item.product?.id == productIdSame }?.product
        val restaurantProductBeforeUpdate = restaurant.products?.find { product -> product.id == productIdSame }
        assertNotEquals(orderBeforeValidate?.price, restaurantProductBeforeUpdate?.price)
        assertNotEquals(orderBeforeValidate?.name, restaurantProductBeforeUpdate?.name)

        // Call the function being tested
        assertThrows<OrderDomainException> {
            OrderDomainServiceImpl().validateAndInitiateOrder(order, restaurant)
        }
    }
}
