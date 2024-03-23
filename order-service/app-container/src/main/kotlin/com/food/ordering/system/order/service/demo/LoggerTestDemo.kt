package com.food.ordering.system.order.service.demo

import com.food.ordering.system.common.valueobject.CustomerId
import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.OrderId
import com.food.ordering.system.common.valueobject.ProductId
import com.food.ordering.system.common.valueobject.RestaurantId
import com.food.ordering.system.order.service.domain.core.entity.Order
import com.food.ordering.system.order.service.domain.core.entity.OrderItem
import com.food.ordering.system.order.service.domain.core.entity.Product
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.core.valueobject.OrderItemId
import com.food.ordering.system.order.service.domain.core.valueobject.StreetAddress
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.FileNotFoundException
import java.math.BigDecimal
import java.util.UUID

@RestController
class LoggerTestDemo {
    companion object {
        val log by LoggerDelegate()
    }

    @GetMapping("/test")
    fun hello(): String {
        log.debug("DEBUG line")
        log.info("INFO line")
        log.warn("WARN line")
        log.error("ERROR line")

        log.info("INFO {} {}", 1, "abc")
        try {
            readFile("nonexistent.txt")
        } catch (e: FileNotFoundException) {
            log.error("ERR failed to compute x", e)
        }
        return "Hello World!: \n" + OrderDemo.order.toString()
    }

    @Throws(FileNotFoundException::class)
    private fun readFile(fileName: String) {
        val file = File(fileName)
        if (!file.exists()) {
            throw FileNotFoundException("File $fileName not found")
        }
    }
}

object OrderDemo {
    val orderId1 = OrderId()
    val customerIdSame = CustomerId()
    val restaurantIdSame = RestaurantId()
    val deliveryAddressSame =
        StreetAddress(
            UUID.randomUUID(),
            "Street 1",
            "123",
            "City 1",
            "Country 1",
        )
    val orderPriceSame = Money.of(BigDecimal(110.0))

    val orderItemsSame =
        mutableListOf(
            OrderItem(
                id = OrderItemId(),
                orderId = orderId1,
                product =
                    Product(
                        id = ProductId(),
                        name = "Product 1",
                        price = Money.of(BigDecimal(10.0)),
                    ),
                quantity = 5,
                price = Money.of(BigDecimal(10.0)),
                subTotal = Money.of(BigDecimal(50.0)),
            ),
            OrderItem(
                id = OrderItemId(),
                orderId = orderId1,
                product =
                    Product(
                        id = ProductId(),
                        name = "Product 2",
                        price = Money.of(BigDecimal(12.0)),
                    ),
                quantity = 5,
                price = Money.of(BigDecimal(12.0)),
                subTotal = Money.of(BigDecimal(60.0)),
            ),
        )

    // Build Order Entity Demo
    val order =
        Order(
            id = orderId1,
            customerId = customerIdSame,
            restaurantId = restaurantIdSame,
            deliveryAddress = deliveryAddressSame,
            price = orderPriceSame,
            orderItems = orderItemsSame,
        )
}
