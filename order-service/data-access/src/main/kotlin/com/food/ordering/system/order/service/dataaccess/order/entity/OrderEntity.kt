package com.food.ordering.system.order.service.dataaccess.order.entity

import com.food.ordering.system.common.valueobject.OrderStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "orders", schema = "food_order")
class OrderEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID? = null,
    @Column(name = "customer_id")
    val customerId: UUID? = null,
    @Column(name = "restaurant_id")
    val restaurantId: UUID? = null,
    @Column(name = "tracking_id")
    val trackingId: UUID? = null,
    @Column(name = "price")
    val price: java.math.BigDecimal? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    val orderStatus: OrderStatus? = null,
    @Column(name = "failure_messages")
    val failureMessages: String? = null,
    @OneToOne(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var address: OrderAddressEntity? = null,
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: List<OrderItemEntity>? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
