package com.food.ordering.system.order.service.domain.core.entity

import com.food.ordering.system.common.entity.BaseEntity
import com.food.ordering.system.common.valueobject.Money
import com.food.ordering.system.common.valueobject.ProductId
import com.food.ordering.system.order.service.domain.core.logging.LoggerDelegate
import org.slf4j.Logger

class Product(
    // IMMUTABLE FIELDS
    id: ProductId = ProductId(),
    // MUTABLE fields
    var name: String? = null,
    var price: Money? = null,
) : BaseEntity<ProductId>(id) {
    companion object {
        val log: Logger by LoggerDelegate()
    }

    fun updateWithConfirmedNameAndPrice(
        name: String,
        price: Money,
    ) {
        name.also { this@Product.name = it }
        price.also { this@Product.price = it }
    }
}
