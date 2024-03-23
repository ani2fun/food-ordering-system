package com.food.ordering.system.order.service.domain.core.valueobject

import com.food.ordering.system.common.valueobject.BaseId
import java.util.Random

// Value objects are immutable and only holds data, so identifier is not important for them.
// That means 2 value objects with same data, but different id’s considered to be the same value object.

data class OrderItemId(override val id: Long = generateUniqueLongId()) : BaseId<Long>(id)

private fun generateUniqueLongId(): Long {
    val randomLong = Random().nextLong()
    return if (randomLong > 0) randomLong else -randomLong
}

