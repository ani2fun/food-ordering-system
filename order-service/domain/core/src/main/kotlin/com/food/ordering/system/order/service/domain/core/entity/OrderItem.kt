package com.food.ordering.system.order.service.domain.core.entity

import com.food.ordering.system.common.entity.BaseEntity
import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.OrderId
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.core.valueobject.OrderItemId
import org.slf4j.Logger

class OrderItem(
    // ENTITY ID
    id: OrderItemId = OrderItemId(),
    // MUTABLE FIELDS
    var orderId: OrderId? = null, // this field will be updated during business logic
    var product: Product? = null,
    var quantity: Int? = null,
    var price: Money? = null,
    var subTotal: Money? = null,
) : BaseEntity<OrderItemId>(id) {
    companion object {
        val log: Logger by LoggerDelegate()
    }

    fun isPriceValid(): Boolean {
        if (this.price == null || this.quantity == null || this.subTotal == null) {
            return false
        }

        val productPrice = product?.price

        // Check if the price is greater than zero, and it matches the product's price
        val priceValid = price!!.isGreaterThanZero() && price == productPrice

        // Calculate the expected subTotal based on the quantity and product price
        val expectedSubTotal = productPrice?.multiply(quantity!!) ?: Money.ZERO

        // Check if the calculated subTotal matches the provided subTotal
        val subTotalValid = expectedSubTotal == subTotal

        return priceValid && subTotalValid
    }
}
