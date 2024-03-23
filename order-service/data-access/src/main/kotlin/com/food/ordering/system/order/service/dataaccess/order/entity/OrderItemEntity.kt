package com.food.ordering.system.order.service.dataaccess.order.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "order_items")
@IdClass(OrderItemEntityId::class)
class OrderItemEntity(
    @Id @Column(name = "id")
    val id: Long? = null,
    @Id @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_id")
    var order: OrderEntity? = null,
    @Column(name = "product_id")
    val productId: UUID? = null,
    @Column(name = "price")
    val price: BigDecimal? = null,
    @Column(name = "quantity")
    val quantity: Int? = null,
    @Column(name = "sub_total")
    val subTotal: BigDecimal? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderItemEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
