package com.food.ordering.system.order.service.domain.core.entity

import com.food.ordering.system.common.entity.AggregateRoot
import com.food.ordering.system.common.valueobject.CustomerId
import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.OrderId
import com.food.ordering.system.common.valueobject.OrderStatus
import com.food.ordering.system.common.valueobject.RestaurantId
import com.food.ordering.system.order.service.domain.core.exception.OrderDomainException
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import com.food.ordering.system.order.service.domain.core.valueobject.StreetAddress
import com.food.ordering.system.order.service.domain.core.valueobject.TrackingId
import org.slf4j.Logger

class Order(
    // ENTITY ID
    id: OrderId = OrderId(),
    // Entity fields
    var customerId: CustomerId? = null,
    var restaurantId: RestaurantId? = null,
    var deliveryAddress: StreetAddress? = null,
    var price: Money? = null,
    var orderItems: List<OrderItem> = mutableListOf(),
    var trackingId: TrackingId? = TrackingId(),
    var orderStatus: OrderStatus? = OrderStatus.PENDING,
    val failureMessages: List<String> = mutableListOf(),
) : AggregateRoot<OrderId>(id) {
    companion object {
        val log: Logger by LoggerDelegate()
    }

    fun validateAndFinaliseOrder() {
        OrderValidation.validateOrder(this@Order.price, this@Order.orderStatus, this@Order.orderItems)
        log.info(
            "Order Validation done successfully!. Returning with Initialized order => {}",
            this.toString(),
        )
    }

    fun updateOrderItems(orderItems: List<OrderItem>) {
        orderItems.also { this@Order.orderItems = it }
    }

    fun updateTrackingId(trackingId: TrackingId) {
        trackingId.also { this@Order.trackingId = it }
    }

    fun updateOrderStatus(orderStatus: OrderStatus) {
        orderStatus.also { this@Order.orderStatus = it }
    }

    fun updateOrderStatusFailureMessages(failureMessages: List<String>) {
        failureMessages.also {
            this@Order.failureMessages.plus(it).filter { it1 ->
                it1.isEmpty()
            }.toMutableList()
        }
    }

    fun pay() {
        if (this@Order.orderStatus != OrderStatus.PENDING) {
            val msg = "Order domain is not in correct state for pay operation!"
            throw OrderDomainException(msg, IllegalStateException(msg))
        }
        this@Order.orderStatus = OrderStatus.PAID
    }

    fun approve() {
        if (this@Order.orderStatus != OrderStatus.PAID) {
            val msg = "Order domain is not in correct state for approve operation!"
            throw OrderDomainException(msg, IllegalStateException(msg))
        }
        this@Order.orderStatus = OrderStatus.APPROVED
    }

    fun initCancel(failureMessages: List<String>) {
        if (this@Order.orderStatus != OrderStatus.PAID) {
            val msg = "Order domain is not in correct state for initCancel operation!"
            throw OrderDomainException(msg, IllegalStateException(msg))
        }
        this@Order.orderStatus = OrderStatus.CANCELLING
        updateOrderStatusFailureMessages(failureMessages)
    }

    fun cancel(failureMessages: List<String>) {
        if (this@Order.orderStatus != OrderStatus.CANCELLING || this@Order.orderStatus != OrderStatus.PENDING) {
            val msg = "Order domain is not in correct state for cancel operation!"
            throw OrderDomainException(msg, IllegalStateException(msg))
        }
        this@Order.orderStatus = OrderStatus.CANCELLED
        updateOrderStatusFailureMessages(failureMessages)
    }

    override fun toString(): String {
        return "Order(id=$id, " +
            "customerId=$customerId, " +
            "restaurantId=$restaurantId, " +
            "deliveryAddress=$deliveryAddress, " +
            "price=$price, " +
            "orderItems=$orderItems, " +
            "trackingId=$trackingId, " +
            "orderStatus=$orderStatus, " +
            "failureMessages=$failureMessages)"
    }
}

object OrderValidation {
    fun validateOrder(
        price: Money?,
        orderStatus: OrderStatus?,
        orderItems: List<OrderItem>,
    ) {
        validateInitialOrder(orderStatus)
        validateTotalPrice(price)
        validateItemsPrice(price, orderItems)
    }

    fun validateInitialOrder(orderStatus: OrderStatus?) {
        checkNotNull(orderStatus) {
            val msg = "Order domain is not in correct state for initialization"
            throw OrderDomainException(msg, IllegalArgumentException(msg))
        }
    }

    fun validateTotalPrice(price: Money?) {
        price?.let {
            if (!price.isGreaterThanZero()) {
                val msg = "Total price must be greater than zero!"
                throw OrderDomainException(msg, IllegalArgumentException(msg))
            }
        }
    }

    fun validateItemsPrice(
        price: Money?,
        items: List<OrderItem>,
    ) {
        val orderItemsTotal = orderItemsTotal(items)
        if (price != orderItemsTotal) {
            val msg =
                "Total price: ${price?.amountAsBigDecimal} is not equal to Order items total: ${orderItemsTotal.amountAsBigDecimal}!"
            throw OrderDomainException(msg, IllegalArgumentException(msg))
        }
    }

    fun orderItemsTotal(items: List<OrderItem>): Money {
        return items.map {
            validateItemPrice(it)
            it.subTotal!!
        }.reduce { acc, money -> acc.add(money) }
    }

    fun validateItemPrice(orderItem: OrderItem) {
        if (!orderItem.isPriceValid()) {
            val msg =
                "Order item price: ${orderItem.price?.amountAsBigDecimal} is not valid for product ${orderItem.product?.id}"
            throw OrderDomainException(msg, IllegalArgumentException(msg))
        }
    }
}
