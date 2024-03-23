package com.food.ordering.system.order.service.domain.core.entity

import com.food.ordering.system.common.entity.AggregateRoot
import com.food.ordering.system.common.valueobject.RestaurantId
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import org.slf4j.Logger

class Restaurant(
    // IMMUTABLE fields
    // ENTITY ID
    id: RestaurantId = RestaurantId(),
    var products: List<Product>? = null,
    var active: Boolean? = null,
) : AggregateRoot<RestaurantId>(id) {
    companion object {
        val log: Logger by LoggerDelegate()
    }

    fun updateWithConfirmedProductsAndActive(
        products: List<Product>,
        active: Boolean,
    ) {
        products.also { this.products = it }
        active.also { this.active = it }
    }
}
