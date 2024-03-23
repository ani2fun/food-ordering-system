package com.food.ordering.system.order.service.domain.dto.create

import com.food.ordering.system.common.valueobject.OrderStatus
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.util.UUID

data class CreateOrderCommand(
    @NotNull val customerId: UUID,
    @NotNull val restaurantId: UUID,
    @NotNull val price: BigDecimal,
    @NotNull val items: List<OrderItem>,
    @NotNull val address: OrderAddress,
)

data class CreateOrderResponse(
    @NotNull val trackingId: UUID,
    @NotNull val orderStatus: OrderStatus,
    @NotNull val message: String,
) {
    override fun toString(): String {
        return "CreateOrderResponse(trackingId = ${this.trackingId}\nOrderStatus = ${this.orderStatus}\nmessage = ${this.message})"
    }
}

data class OrderItem(
    @NotNull val productId: UUID,
    @NotNull @Max(value = 50) val quantity: Int,
    @NotNull val price: BigDecimal,
    @NotNull val subTotal: BigDecimal,
)

data class OrderAddress(
    @NotNull @Max(value = 50) val street: String,
    @NotNull @Max(value = 10) val postalCode: String,
    @NotNull @Max(value = 50) val city: String,
    @NotNull @Max(value = 50) val country: String,
)
