package com.food.ordering.system.order.service.dataaccess.order.entity

import java.io.Serializable

open class OrderItemEntityId(
    var id: Long? = null,
    var order: OrderEntity? = null,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderItemEntityId) return false

        if (id != other.id) return false
        if (order != other.order) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (order?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "OrderItemEntityId(id=$id, order=$order)"
    }
}
