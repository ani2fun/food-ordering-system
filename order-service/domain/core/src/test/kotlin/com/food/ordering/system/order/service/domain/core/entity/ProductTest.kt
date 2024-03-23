package com.food.ordering.system.order.service.domain.core.entity

import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.ProductId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.UUID

class ProductTest {

    private lateinit var product: Product

    @BeforeEach
    fun setUp() {
        val productId = ProductId(UUID.randomUUID())
        product = Product(id = productId, name = "Sample Product", price = Money.of(BigDecimal("10.00")))
    }

    @Test
    fun testEquals() {
        val productIdSame = ProductId(UUID.randomUUID())
        val product1 = Product(id = productIdSame, name = "Product 1", price = Money.of(BigDecimal("10.00")))
        val product2 = Product(id = productIdSame, name = "Product 2", price = Money.of(BigDecimal("20.00")))

        // WHEN IDS ARE EQUAL THE ENTITIES MUST BE EQUAL
        assertEquals(product1, product2)

        // WHEN IDS ARE NOT EQUAL THE ENTITIES MUST NOT BE EQUAL EVEN THOUGH OTHER PARAMS ARE EQUAL
        val differentProduct = Product(ProductId(UUID.randomUUID()), "Product 1", Money.of(BigDecimal("10.00")))
        assertNotEquals(product1, differentProduct)

    }


    @Test
    fun testUpdateWithConfirmedNameAndPrice() {
        val newName = "Updated Product"
        val newPrice = Money.of(BigDecimal("15.00"))

        product.updateWithConfirmedNameAndPrice(newName, newPrice)

        assertEquals(newName, product.name)
        assertEquals(newPrice, product.price)
    }
}
