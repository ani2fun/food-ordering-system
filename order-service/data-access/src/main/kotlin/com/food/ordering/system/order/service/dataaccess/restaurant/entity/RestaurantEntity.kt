package com.food.ordering.system.order.service.dataaccess.restaurant.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.util.UUID

@Entity
// @Table(name = "restaurant")
@Table(name = "order_restaurant_m_view", schema = "restaurant")
@IdClass(RestaurantEntityId::class)
class RestaurantEntity(
    @Id @Column(name = "restaurant_id")
    val restaurantId: UUID? = null,
    @Id @Column(name = "product_id")
    val productId: UUID? = null,
    @Column(name = "restaurant_name")
    val restaurantName: String? = null,
    @Column(name = "restaurant_active")
    val restaurantActive: Boolean? = null,
    @Column(name = "product_name")
    val productName: String? = null,
    @Column(name = "product_price")
    val productPrice: java.math.BigDecimal? = null,
    @Column(name = "product_available")
    val productAvailable: Boolean? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RestaurantEntity) return false

        if (restaurantId != other.restaurantId) return false
        if (productId != other.productId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = restaurantId?.hashCode() ?: 0
        result = 31 * result + (productId?.hashCode() ?: 0)
        return result
    }
}
