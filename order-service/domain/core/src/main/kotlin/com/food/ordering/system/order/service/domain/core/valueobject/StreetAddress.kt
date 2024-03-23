package com.food.ordering.system.order.service.domain.core.valueobject

import java.util.UUID

data class StreetAddress(
    val id: UUID = UUID.randomUUID(),
    val street: String,
    val postalCode: String,
    val city: String,
    val country: String
)
