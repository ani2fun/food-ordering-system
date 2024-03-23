package com.food.ordering.system.common.valueobject

import java.util.UUID

data class CustomerId(override val id: UUID = UUID.randomUUID()) : BaseId<UUID>(id)

data class OrderId(override val id: UUID = UUID.randomUUID()) : BaseId<UUID>(id)

data class RestaurantId(override val id: UUID = UUID.randomUUID()) : BaseId<UUID>(id)

data class ProductId(override val id: UUID = UUID.randomUUID()) : BaseId<UUID>(id)

