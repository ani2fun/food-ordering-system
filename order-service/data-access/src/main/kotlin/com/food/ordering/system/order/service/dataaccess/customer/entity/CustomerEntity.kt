package com.food.ordering.system.order.service.dataaccess.customer.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
// @Table(name = "customers")
@Table(name = "order_customer_m_view", schema = "customer")
class CustomerEntity(
    @Id
    val id: UUID? = null,
    @Column(name = "username")
    val username: String? = null,
    @Column(name = "first_name")
    val firstName: String? = null,
    @Column(name = "last_name")
    val lastName: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CustomerEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
