package com.food.ordering.system.order.service.dataaccess.restaurant.entity

import java.io.Serializable
import java.util.UUID

open class RestaurantEntityId(
    var restaurantId: UUID? = null,
    var productId: UUID? = null,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RestaurantEntityId) return false

        if (restaurantId != other.restaurantId) return false
        if (productId != other.productId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = restaurantId?.hashCode() ?: 0
        result = 31 * result + (productId?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "RestaurantEntityId(restaurantId=$restaurantId, productId=$productId)"
    }
}
