package com.food.ordering.system.order.service.dataaccess.order.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "order_address")
class OrderAddressEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID? = null,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_id")
    var order: OrderEntity? = null,
    @Column(name = "street")
    val street: String? = null,
    @Column(name = "postal_code")
    val postalCode: String? = null,
    @Column(name = "city")
    val city: String? = null,
    @Column(name = "country")
    val country: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderAddressEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
